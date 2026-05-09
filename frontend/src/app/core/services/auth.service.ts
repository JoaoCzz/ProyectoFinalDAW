import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';
import { AuthLoginRequest, AuthCreateUserRequest, AuthResponse, User } from '../models/auth.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();
  private logoutTimer: ReturnType<typeof setTimeout> | null = null;

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.loadUser();
  }

  login(request: AuthLoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/log-in`, request);
  }

  register(request: AuthCreateUserRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/sing-up`, request);
  }

  logout(redirectToLogin: boolean = true): void {
    this.clearLogoutTimer();
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    this.currentUserSubject.next(null);

    if (redirectToLogin) {
      this.router.navigate(['/auth/login']);
    }
  }

  setToken(token: string): void {
    localStorage.setItem('token', token);
    this.scheduleAutoLogout(token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isAuthenticated(): boolean {
    const token = this.getToken();

    if (!token) {
      return false;
    }

    if (this.isTokenExpired(token)) {
      this.logout();
      return false;
    }

    return true;
  }

  setCurrentUser(user: User): void {
    localStorage.setItem('user', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  loadUser(): void {
    const user = localStorage.getItem('user');
    const token = this.getToken();

    if (token) {
      if (this.isTokenExpired(token)) {
        this.logout(false);
        return;
      }

      this.scheduleAutoLogout(token);
    }

    if (user) {
      this.currentUserSubject.next(JSON.parse(user));
    }
  }

  isTokenExpired(token: string): boolean {
    const payload = this.decodeTokenPayload(token);

    if (!payload?.exp) {
      return false;
    }

    return Date.now() >= payload.exp * 1000;
  }

  private scheduleAutoLogout(token: string): void {
    this.clearLogoutTimer();

    const payload = this.decodeTokenPayload(token);
    if (!payload?.exp) {
      return;
    }

    const expiresAt = payload.exp * 1000;
    const timeout = expiresAt - Date.now();

    if (timeout <= 0) {
      this.logout();
      return;
    }

    this.logoutTimer = setTimeout(() => {
      this.logout();
    }, timeout);
  }

  private clearLogoutTimer(): void {
    if (this.logoutTimer) {
      clearTimeout(this.logoutTimer);
      this.logoutTimer = null;
    }
  }

  private decodeTokenPayload(token: string): { exp?: number } | null {
    try {
      const payload = token.split('.')[1];
      if (!payload) {
        return null;
      }

      const normalizedPayload = payload.replace(/-/g, '+').replace(/_/g, '/');
      const paddedPayload = normalizedPayload.padEnd(
        normalizedPayload.length + ((4 - (normalizedPayload.length % 4)) % 4),
        '='
      );
      const decodedPayload = atob(paddedPayload);
      return JSON.parse(decodedPayload);
    } catch {
      return null;
    }
  }
}
