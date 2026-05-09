export interface Comment {
  id: number;
  description: string;
  createdAt: string;
  userId: number;
  username: string;
  postId: number;
  totalLikes: number;
  likedByCurrentUser: boolean;
}

export interface CommentRequest {
  description: string;
  postId: number;
}

export interface CommentResponse extends Comment {}

export interface CommentPage {
  content: Comment[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
}
