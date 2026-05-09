import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { PostService } from '../../core/services/post.service';
import { LikeService } from '../../core/services/like.service';
import { AuthService } from '../../core/services/auth.service';
import { GenreService } from '../../core/services/genre.service';
import { Post } from '../../core/models/post.model';
import { Genre } from '../../core/models/genre.model';

@Component({
  selector: 'app-posts-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './posts-list.component.html',
  styleUrls: ['./posts-list.component.css']
})
export class PostsListComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  posts: Post[] = [];
  filteredPosts: Post[] = [];
  isLoading = true;
  errorMessage = '';
  searchQuery = '';
  currentPage = 0;
  pageSize = 10;
  totalPages = 0;
  isAuthenticated = false;
  genres: Genre[] = [];
  selectedGenreId: number | null = null;
  genreChipSearch = '';
  activeTab: 'recommendations' | 'promotions' = 'recommendations';

  get filteredGenresForChips(): Genre[] {
    return this.genres.filter(g => 
      g.name.toLowerCase().includes(this.genreChipSearch.toLowerCase())
    );
  }

  get displayedPosts(): Post[] {
    return this.filteredPosts.filter(p => 
      this.activeTab === 'recommendations' ? !p.isSelfPromotion : p.isSelfPromotion
    );
  }

  constructor(
    private postService: PostService,
    private likeService: LikeService,
    private authService: AuthService,
    private genreService: GenreService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.isAuthenticated = this.authService.isAuthenticated();
    this.loadGenres();
    this.loadPosts();
  }

  loadGenres(): void {
    this.genreService.getAllGenres()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (genres) => {
          this.genres = genres;
          this.cdr.markForCheck();
        },
        error: (err) => console.error('Error loading genres', err)
    });
  }

  loadPosts(): void {
    this.isLoading = true;
    this.errorMessage = '';
    this.cdr.markForCheck();

    if (this.selectedGenreId) {
      this.postService.getPostsByGenre(this.selectedGenreId, this.currentPage, this.pageSize)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (response) => {
            this.posts = response.content;
            this.filteredPosts = this.posts;
            this.totalPages = response.totalPages;
            this.isLoading = false;
            this.cdr.markForCheck();
          },
          error: (err) => {
            console.error('Error loading posts for genre:', err);
            this.errorMessage = 'Failed to load posts for this genre';
            this.isLoading = false;
            this.cdr.markForCheck();
          }
        });
    } else {
      this.postService.getAllPosts(this.currentPage, this.pageSize)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (response) => {
            this.posts = response.content;
            this.filteredPosts = this.posts;
            this.totalPages = response.totalPages;
            this.isLoading = false;
            this.cdr.markForCheck();
          },
          error: (err) => {
            console.error('Error loading posts:', err);
            this.errorMessage = 'Failed to load posts';
            this.isLoading = false;
            this.cdr.markForCheck();
          }
      });
    }
  }

  filterByGenre(genreId: number | null): void {
    this.selectedGenreId = genreId;
    this.currentPage = 0;
    this.searchQuery = ''; // Limpiar búsqueda
    this.genreChipSearch = ''; // Limpiar filtro de chips
    this.loadPosts();
  }

   switchTab(tab: 'recommendations' | 'promotions'): void {
    this.activeTab = tab;
    this.currentPage = 0;
    this.searchQuery = ''; // Limpiar búsqueda
    this.selectedGenreId = null; // Limpiar filtro de género
    this.genreChipSearch = ''; // Limpiar filtro de chips
    this.loadPosts();
  }

  searchPosts(): void {
    if (!this.searchQuery.trim()) {
      this.filteredPosts = this.posts;
      this.cdr.markForCheck();
      return;
    }

    this.isLoading = true;
    this.postService.searchPosts(this.searchQuery, this.currentPage, this.pageSize)
    .pipe(takeUntil(this.destroy$))
    .subscribe({
      next: (response) => {
        this.filteredPosts = response.content;
        this.totalPages = response.totalPages;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error:', err);
        this.errorMessage = 'Search failed';
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  toggleLike(post: Post): void {
    if (!this.isAuthenticated) {
      this.router.navigate(['/auth/login']);
      return;
    }

    this.likeService.toggleLikePost(post.id)
    .pipe(takeUntil(this.destroy$))
    .subscribe({
      next: (response) => {
        post.totalLikes = response.totalLikes;
        post.likedByCurrentUser = response.likedByCurrentUser;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error toggling like', err);
      }
    });
  }

  viewPost(postId: number): void {
    this.router.navigate(['/posts', postId]);
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadPosts();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadPosts();
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
