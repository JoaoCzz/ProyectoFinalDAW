import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../core/services/user.service';
import { User } from '../../core/models/auth.model';
import { ArtistService } from '../../core/services/artist.service';
import { GenreService } from '../../core/services/genre.service';
import { Artist } from '../../core/models/artist.model';
import { Genre } from '../../core/models/genre.model';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  users: User[] = [];
  artists: Artist[] = [];
  genres: Genre[] = [];
  isLoading = true;
  errorMessage = '';
  successMessage = '';
  
  availableRoles = ['USER', 'ARTIST', 'ADMIN', 'INVITED', 'DEVELOPER'];

  // Artist form
  newArtistName = '';
  newArtistType = '';

  // Genre form
  newGenreName = '';
  // Modal state
  showArtistModal = false;
  showGenreModal = false;
  showUsersModal = false;
  // (User creation removed) Only role management is available here

  constructor(
    private userService: UserService,
    private cdr: ChangeDetectorRef,
    private artistService: ArtistService,
    private genreService: GenreService
  ) {}

  ngOnInit(): void {
    this.loadUsers();
    this.loadArtists();
    this.loadGenres();
  }

  loadUsers(): void {
    this.isLoading = true;
    this.errorMessage = '';
    console.log('Iniciando carga de usuarios...');

    this.userService.getAllUsers().subscribe({
      next: (users) => {
        console.log('Usuarios cargados con éxito:', users);
        this.users = users;
        this.isLoading = false;
        this.cdr.detectChanges(); // Forzamos el renderizado inmediato
      },
      error: (err) => {
        console.error('Error al cargar usuarios:', err);
        this.errorMessage = `Error (${err.status}): ${err.error?.message || 'Asegúrate de tener el rol ADMIN.'}`;
        this.isLoading = false;
        this.cdr.detectChanges();
      }
    });
  }

  loadArtists(): void {
    this.artistService.getAllArtists().subscribe({
      next: (items) => { this.artists = items; this.cdr.detectChanges(); },
      error: (err) => { console.error('Error cargando artistas', err); }
    });
  }

  loadGenres(): void {
    this.genreService.getAllGenres().subscribe({
      next: (items) => { this.genres = items; this.cdr.detectChanges(); },
      error: (err) => { console.error('Error cargando géneros', err); }
    });
  }

  createArtist(): void {
    if (!this.newArtistName || this.newArtistName.trim().length === 0) return;
    const payload: any = { name: this.newArtistName };
    if (this.newArtistType) payload.type = this.newArtistType;
    this.artistService.createArtist(payload).subscribe({
      next: (res) => {
        this.successMessage = `Artista ${res.name} creado`;
        this.newArtistName = '';
        this.newArtistType = '';
        this.loadArtists();
        this.closeArtistModal();
        setTimeout(() => this.successMessage = '', 3000);
      },
      error: (err) => { this.errorMessage = 'Error creando artista'; setTimeout(() => this.errorMessage = '', 3000); }
    });
  }

  createGenre(): void {
    if (!this.newGenreName || this.newGenreName.trim().length === 0) return;
    const payload = { name: this.newGenreName };
    this.genreService.createGenre(payload).subscribe({
      next: (res) => {
        this.successMessage = `Género ${res.name} creado`;
        this.newGenreName = '';
        this.loadGenres();
        this.closeGenreModal();
        setTimeout(() => this.successMessage = '', 3000);
      },
      error: (err) => { this.errorMessage = 'Error creando género'; setTimeout(() => this.errorMessage = '', 3000); }
    });
  }

  openArtistModal(): void { this.showArtistModal = true; }
  closeArtistModal(): void { this.showArtistModal = false; this.newArtistName = ''; this.newArtistType = ''; }

  openGenreModal(): void { this.showGenreModal = true; }
  closeGenreModal(): void { this.showGenreModal = false; this.newGenreName = ''; }

  openUsersModal(): void { this.showUsersModal = true; }
  closeUsersModal(): void { this.showUsersModal = false; }

  createUser(): void {
    // Removed: user creation is not allowed via admin panel UI.
  }

  trackByUserId(index: number, user: User): number {
    return user.id;
  }

  updateRole(user: User, event: any): void {
    const newRole = event.target.value;
    const roles = [newRole];
    
    this.userService.updateRoles(user.id, roles).subscribe({
      next: () => {
        this.successMessage = `Rol de ${user.username} actualizado a ${newRole}`;
        user.roles = roles;
        setTimeout(() => this.successMessage = '', 3000);
      },
      error: (err) => {
        this.errorMessage = 'Error al actualizar el rol.';
        setTimeout(() => this.errorMessage = '', 3000);
      }
    });
  }

  trackByArtistId(index: number, a: Artist): number {
    return a.id;
  }

  trackByGenreId(index: number, g: Genre): number {
    return g.id;
  }
}
