import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Artist, ArtistRequest, ArtistResponse } from '../models/artist.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ArtistService {
  private apiUrl = `${environment.apiUrl}/artists`;

  constructor(private http: HttpClient) {}

  createArtist(request: ArtistRequest): Observable<ArtistResponse> {
    return this.http.post<ArtistResponse>(this.apiUrl, request);
  }

  getArtistById(id: number): Observable<ArtistResponse> {
    return this.http.get<ArtistResponse>(`${this.apiUrl}/${id}`);
  }

  getAllArtists(): Observable<Artist[]> {
    return this.http.get<Artist[]>(this.apiUrl);
  }

  updateArtist(id: number, request: ArtistRequest): Observable<ArtistResponse> {
    return this.http.put<ArtistResponse>(`${this.apiUrl}/${id}`, request);
  }

  deleteArtist(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
