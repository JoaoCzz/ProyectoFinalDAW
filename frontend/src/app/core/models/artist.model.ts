export interface Artist {
  id: number;
  name: string;
  email: string;
  type: string;
}

export interface ArtistRequest {
  name: string;
  email: string;
  type: string;
}

export interface ArtistResponse extends Artist {}
