import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { PostService } from '../../core/services/post.service';
import { CommentService } from '../../core/services/comment.service';
import { LikeService } from '../../core/services/like.service';
import { AuthService } from '../../core/services/auth.service';
import { Post } from '../../core/models/post.model';
import { Comment, CommentRequest } from '../../core/models/comment.model';
import { finalize } from 'rxjs/operators';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-post-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.css']
})
export class PostDetailComponent implements OnInit {
  post: Post | null = null;
  preview: any = null;
  safeEmbedHtml: SafeHtml | null = null;
  comments: Comment[] = [];
  newCommentText = '';
  isLoading = true;
  isSubmittingComment = false;
  errorMessage = '';
  isAuthenticated = false;
  currentUser: any = null;
  currentUserId = 0;
  currentPage = 0;
  pageSize = 10;
  totalPages = 0;

  constructor(
    private postService: PostService,
    private commentService: CommentService,
    private likeService: LikeService,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
    ,
    private cdr: ChangeDetectorRef
    ,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.isAuthenticated = this.authService.isAuthenticated();
    const user = this.authService.getCurrentUser();
    if (user) {
      this.currentUser = user;
      this.currentUserId = user.id;
    }

    this.route.params.subscribe(params => {
      const postId = +params['id'];
      if (isNaN(postId) || postId <= 0) {
        console.error('Invalid post id in route:', params['id']);
        this.errorMessage = 'Invalid post id';
        this.isLoading = false;
        return;
      }

      this.loadPost(postId);
      this.loadComments(postId);
    });
  }

  private loadPost(postId: number): void {
    console.log('Iniciando carga de post:', postId);
    this.isLoading = true;
    this.postService.getPostById(postId)
      .pipe(finalize(() => {
        this.isLoading = false;
        console.log('Finalizado loadPost, isLoading:', this.isLoading);
        this.cdr.detectChanges();
      }))
      .subscribe({
        next: (post) => {
          console.log('Post recibido:', post);
          this.post = post;
          if (post?.musicUrl) {
            this.postService.getPreview(post.musicUrl).subscribe({
              next: (pv) => {
                console.log('Preview recibido:', pv);
                this.preview = pv;
                if (pv?.embedHtml) {
                  this.safeEmbedHtml = this.sanitizer.bypassSecurityTrustHtml(pv.embedHtml);
                }
                this.cdr.detectChanges();
              },
              error: (err) => console.error('Error fetching preview', err)
            });
          }
        },
        error: (err) => {
          console.error('Error loading post', err);
          this.errorMessage = 'Failed to load post';
        }
      });
  }

  private loadComments(postId: number): void {
    console.log('Iniciando carga de comentarios para post:', postId);
    this.commentService.getCommentsByPost(postId, this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        console.log('Comentarios recibidos:', response.content.length);
        this.comments = response.content;
        this.totalPages = response.totalPages;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error loading comments', err);
      }
    });
  }

  submitComment(): void {
    if (!this.isAuthenticated) {
      this.router.navigate(['/auth/login']);
      return;
    }

    if (!this.newCommentText.trim() || !this.post) {
      return;
    }

    this.isSubmittingComment = true;
    const commentRequest: CommentRequest = {
      description: this.newCommentText,
      postId: this.post.id
    };

    this.commentService.createComment(commentRequest).subscribe({
      next: (comment) => {
        this.comments.unshift(comment);
        if (this.post) {
          this.post.totalComments += 1;
        }
        this.newCommentText = '';
        this.isSubmittingComment = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.errorMessage = 'Failed to post comment';
        this.isSubmittingComment = false;
      }
    });
  }

  toggleLike(): void {
    if (!this.isAuthenticated) {
      this.router.navigate(['/auth/login']);
      return;
    }

    if (!this.post) return;

    this.likeService.toggleLikePost(this.post.id).subscribe({
      next: (response) => {
        if (this.post) {
          this.post.totalLikes = response.totalLikes;
          this.post.likedByCurrentUser = response.likedByCurrentUser;
        }
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error toggling like', err);
      }
    });
  }

  toggleCommentLike(comment: Comment): void {
    if (!this.isAuthenticated) {
      this.router.navigate(['/auth/login']);
      return;
    }

    this.likeService.toggleLikeComment(comment.id).subscribe({
      next: (response) => {
        comment.totalLikes = response.totalLikes;
        comment.likedByCurrentUser = response.likedByCurrentUser;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error toggling comment like', err);
      }
    });
  }

  deleteComment(commentId: number): void {
    if (confirm('Are you sure you want to delete this comment?')) {
      this.commentService.deleteComment(commentId).subscribe({
        next: () => {
          this.comments = this.comments.filter(c => c.id !== commentId);
          if (this.post && this.post.totalComments > 0) {
            this.post.totalComments -= 1;
          }
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.errorMessage = 'Failed to delete comment';
        }
      });
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      if (this.post) {
        this.loadComments(this.post.id);
      }
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      if (this.post) {
        this.loadComments(this.post.id);
      }
    }
  }

  goBack(): void {
    this.router.navigate(['/posts']);
  }
}
