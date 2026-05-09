import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LikeResponse } from '../models/like.model';

@Injectable({
  providedIn: 'root'
})
export class LikeService {
  private apiUrl = 'http://localhost:8080/likes';

  constructor(private http: HttpClient) {}

  toggleLikePost(postId: number): Observable<LikeResponse> {
    return this.http.post<LikeResponse>(`${this.apiUrl}/post/${postId}`, {});
  }

  toggleLikeComment(commentId: number): Observable<LikeResponse> {
    return this.http.post<LikeResponse>(`${this.apiUrl}/comment/${commentId}`, {});
  }
}
