import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post, PostRequest, PostResponse } from '../models/post.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiUrl = `${environment.apiUrl}/posts`;
  private previewUrl = `${environment.apiUrl}/api/preview`;

  constructor(private http: HttpClient) {}

  createPost(request: PostRequest): Observable<PostResponse> {
    return this.http.post<PostResponse>(this.apiUrl, request);
  }

  getPostById(id: number): Observable<PostResponse> {
    return this.http.get<PostResponse>(`${this.apiUrl}/${id}`);
  }

  getAllPosts(page: number = 0, size: number = 10): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', 'createdAt,desc');
    return this.http.get<any>(this.apiUrl, { params });
  }

  searchPosts(q: string, page: number = 0, size: number = 10): Observable<any> {
    const params = new HttpParams()
      .set('q', q)
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', 'createdAt,desc');
    return this.http.get<any>(`${this.apiUrl}/search`, { params });
  }

  getPostsByUser(userId: number): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}/user/${userId}`);
  }

  getPostsByArtist(artistId: number): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}/artist/${artistId}`);
  }

  getPostsByGenre(genreId: number, page: number = 0, size: number = 10): Observable<any> {
      const params = new HttpParams()
        .set('page', page.toString())
        .set('size', size.toString())
        .set('sort', 'createdAt,desc');
      return this.http.get<any>(`${this.apiUrl}/genre/${genreId}`, { params });
  }   

  updatePost(id: number, request: PostRequest): Observable<PostResponse> {
    return this.http.put<PostResponse>(`${this.apiUrl}/${id}`, request);
  }

  deletePost(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getPreview(url: string): Observable<any> {
    const params = new HttpParams().set('url', url);
    return this.http.get<any>(this.previewUrl, { params });
  }
}
