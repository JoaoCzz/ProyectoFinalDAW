import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { PostService } from '../../core/services/post.service';
import { GenreService } from '../../core/services/genre.service';
import { ArtistService } from '../../core/services/artist.service';
import { AuthService } from '../../core/services/auth.service';
import { PostRequest } from '../../core/models/post.model';
import { Genre } from '../../core/models/genre.model';
import { Artist } from '../../core/models/artist.model';

@Component({
  selector: 'app-create-post',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  postData: PostRequest = {
    title: '',
    description: '',
    musicUrl: '',
    isSelfPromotion: false,
    artistId: 0,
    genreId: 0
  };

  genres: Genre[] = [];
  artists: Artist[] = [];
  artistSearch = '';
  genreSearch = '';
  isLoading = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private postService: PostService,
    private genreService: GenreService,
    private artistService: ArtistService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef,
    public router: Router
  ) {}

  get filteredArtists(): Artist[] {
    return this.artists.filter(a => 
      a.name.toLowerCase().includes(this.artistSearch.toLowerCase())
    );
  }

  get filteredGenres(): Genre[] {
    return this.genres.filter(g => 
      g.name.toLowerCase().includes(this.genreSearch.toLowerCase())
    );
  }

  ngOnInit(): void {
    this.loadGenresAndArtists();
  }

  private loadGenresAndArtists(): void {
    console.log('Solicitando géneros y artistas para el formulario...');
    this.genreService.getAllGenres().subscribe({
      next: (genres) => {
        console.log('Géneros recibidos en el formulario:', genres);
        this.genres = genres;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error loading genres', err)
    });

    this.artistService.getAllArtists().subscribe({
      next: (artists) => {
        console.log('Artistas recibidos en el formulario:', artists);
        this.artists = artists;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error loading artists', err)
    });
  }

  onCreatePost(): void {
    if (!this.isFormValid()) {
      this.errorMessage = 'Please fill all required fields';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.postService.createPost(this.postData).subscribe({
      next: (response) => {
        this.successMessage = 'Post created successfully!';
        this.isLoading = false;
        setTimeout(() => {
          this.router.navigate(['/posts', response.id]);
        }, 1000);
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Failed to create post';
        this.isLoading = false;
      }
    });
  }

  private isFormValid(): boolean {
    const isArtistValid = this.postData.isSelfPromotion || (this.postData.artistId && this.postData.artistId > 0);
    return !!(
      this.postData.title &&
      isArtistValid &&
      this.postData.genreId
    );
  }
}
