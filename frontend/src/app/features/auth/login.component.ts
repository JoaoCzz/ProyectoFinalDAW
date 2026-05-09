import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { UserService } from '../../core/services/user.service';
import { AuthLoginRequest } from '../../core/models/auth.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  credentials: AuthLoginRequest = {
    username: '',
    password: ''
  };
  isLoading = false;
  errorMessage = '';

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private router: Router
  ) {}

  onLogin(): void {
    if (!this.credentials.username || !this.credentials.password) {
      this.errorMessage = 'Please fill all fields';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        this.authService.setToken(response.jwt);
        
        // Fetch full profile to get roles and email
        this.userService.getMyProfile().subscribe({
          next: (user) => {
            console.log('User profile received:', user);
            console.log('User roles:', user.roles);
            this.authService.setCurrentUser(user);
            this.router.navigate(['/posts']);
          },
          error: (err) => {
            console.error('Failed to fetch profile:', err);
            // Fallback if profile fails
            const fallbackUser = {
              username: response.username,
              id: 0,
              name: '',
              surname: '',
              email: '',
              birthDay: '',
              createdAt: '',
              roles: []
            };
            this.authService.setCurrentUser(fallbackUser);
            this.router.navigate(['/posts']);
          }
        });
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Login failed. Please try again.';
        this.isLoading = false;
      }
    });
  }
}
