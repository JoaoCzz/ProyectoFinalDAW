import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-not-authorized',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="error-container">
      <div class="error-box">
        <h1>Access Denied</h1>
        <p>You do not have permission to access this resource.</p>
        <p *ngIf="!isAuthenticated">Please <a routerLink="/auth/login">login</a> to continue.</p>
        <button routerLink="/posts" class="btn-home">Go to Home</button>
      </div>
    </div>
  `,
  styles: [`
    .error-container {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      padding: 20px;
    }

    .error-box {
      background: white;
      padding: 40px;
      border-radius: 10px;
      box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
      text-align: center;
      max-width: 400px;
    }

    .error-box h1 {
      color: #dc3545;
      margin-bottom: 20px;
      font-size: 32px;
    }

    .error-box p {
      color: #666;
      margin-bottom: 15px;
      line-height: 1.6;
    }

    .error-box a {
      color: #667eea;
      text-decoration: none;
      font-weight: 500;
    }

    .error-box a:hover {
      text-decoration: underline;
    }

    .btn-home {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: white;
      padding: 12px 24px;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      font-weight: 500;
      margin-top: 20px;
      transition: opacity 0.3s;
    }

    .btn-home:hover {
      opacity: 0.9;
    }
  `]
})
export class NotAuthorizedComponent {
  isAuthenticated = false;
}
