// import { Injectable } from '@angular/core';
//
// @Injectable({
//   providedIn: 'root'
// })
// export class MovieService {
//
//   constructor() { }
// }
//

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class MovieService {
  private apiUrl = 'http://localhost:8091/api/v1/movies/search';

  constructor(private http: HttpClient) {}

  searchMoviesByTitle(title: string): Observable<any[]> {
    // const token = localStorage.getItem('token');
    // const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any[]>(`${this.apiUrl}?title=${title}`);
  }
}
