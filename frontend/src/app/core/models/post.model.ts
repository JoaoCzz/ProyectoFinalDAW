export interface Post {
  id: number;
  title: string;
  description: string;
  musicUrl?: string;
  isSelfPromotion: boolean;
  createdAt: string;
  userId: number;
  username: string;
  artistId: number;
  artistName: string;
  genreId: number;
  genreName: string;
  totalLikes: number;
  totalComments: number;
}

export interface PostRequest {
  title: string;
  description: string;
  musicUrl: string;
  isSelfPromotion: boolean;
  artistId: number;
  genreId: number;
}

export interface PostResponse extends Post {}

export interface PostPage {
  content: Post[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
}
