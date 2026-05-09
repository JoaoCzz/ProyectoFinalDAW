import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Genre, GenreRequest, GenreResponse } from '../models/genre.model';

@Injectable({
  providedIn: 'root'
})
export class GenreService {
  private apiUrl = 'http://localhost:8080/genres';

  constructor(private http: HttpClient) {}

  createGenre(request: GenreRequest): Observable<GenreResponse> {
    return this.http.post<GenreResponse>(this.apiUrl, request);
  }

  getGenreById(id: number): Observable<GenreResponse> {
    return this.http.get<GenreResponse>(`${this.apiUrl}/${id}`);
  }

  getAllGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>(this.apiUrl);
  }

  updateGenre(id: number, request: GenreRequest): Observable<GenreResponse> {
    return this.http.put<GenreResponse>(`${this.apiUrl}/${id}`, request);
  }

  deleteGenre(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
