import { Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { AdminGuard } from './core/guards/admin.guard';
import { LoginComponent } from './features/auth/login.component';
import { RegisterComponent } from './features/auth/register.component';
import { LegalComponent } from './features/legal/legal.component';
import { PostsListComponent } from './features/posts/posts-list.component';
import { PostDetailComponent } from './features/posts/post-detail.component';
import { CreatePostComponent } from './features/posts/create-post.component';
import { NotAuthorizedComponent } from './shared/components/not-authorized.component';

export const routes: Routes = [
  { path: '', redirectTo: '/posts', pathMatch: 'full' },
  
  {
    path: 'auth',
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent }
    ]
  },

  {
    path: 'posts',
    children: [
      { path: '', component: PostsListComponent },
      { path: 'create', component: CreatePostComponent, canActivate: [AuthGuard] },
      { path: ':id', component: PostDetailComponent }
    ]
  },

  { path: 'legal', component: LegalComponent },

  {
    path: 'admin',
    loadComponent: () => import('./features/admin/admin.component').then(m => m.AdminComponent),
    canActivate: [AuthGuard, AdminGuard]
  },

  {
    path: 'profile',
    loadComponent: () => import('./features/profile/profile.component').then(m => m.ProfileComponent),
    canActivate: [AuthGuard]
  },

  { path: 'not-authorized', component: NotAuthorizedComponent },
  { path: '**', redirectTo: '/posts' }
];
