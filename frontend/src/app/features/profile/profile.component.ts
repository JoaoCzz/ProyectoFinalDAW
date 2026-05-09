import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../core/services/user.service';
import { AuthService } from '../../core/services/auth.service';
import { User } from '../../core/models/auth.model';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: User | null = null;
  oldPassword = '';
  newPassword = '';
  confirmPassword = '';
  isLoading = false;
  successMessage = '';
  errorMessage = '';

  constructor(
    private userService: UserService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.userService.getMyProfile().subscribe({
      next: (user) => {
        this.user = user;
        // Update the cached user in auth service so navbar stays in sync
        this.authService.setCurrentUser(user);
      },
      error: () => {
        // Fallback to cached user if fetch fails
        this.user = this.authService.getCurrentUser();
      }
    });
  }

  updatePassword(): void {
    if (!this.oldPassword) {
      this.errorMessage = 'Debes ingresar tu contraseña actual.';
      return;
    }

    if (this.newPassword !== this.confirmPassword) {
      this.errorMessage = 'Las contraseñas no coinciden.';
      return;
    }

    if (this.newPassword.length < 4) {
      this.errorMessage = 'La nueva contraseña es demasiado corta.';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.userService.updatePassword(this.oldPassword, this.newPassword).subscribe({
      next: () => {
        this.successMessage = 'Contraseña actualizada correctamente.';
        this.oldPassword = '';
        this.newPassword = '';
        this.confirmPassword = '';
        this.isLoading = false;
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Error al actualizar la contraseña (verifica tu contraseña actual).';
        this.isLoading = false;
      }
    });
  }
}
