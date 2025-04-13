

import { Component } from '@angular/core';
import { MovieService } from '../../services/movie.service';
import { FormsModule } from '@angular/forms';
import {CommonModule} from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const token = localStorage.getItem('Access-token');
const headers = { 'Authorization': `Bearer ${token}` };

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  imports: [CommonModule, FormsModule],
})
export class AdminDashboardComponent {

  searchTitle: string = '';
  movies: any[] = [];

  constructor(
    private movieService: MovieService,
    private http: HttpClient
  ) {}


  searchMovies() {
    this.movieService.searchMoviesByTitle(this.searchTitle).subscribe({
      next: (data) => this.movies = data,
      error: (err) => console.error('Failed to fetch movies:', err)
    });
  }

  addMovie(movie: any) {
    const token = localStorage.getItem('Access-token');
    const headers = { 'Authorization': `Bearer ${token}` };
    console.log("token    "+token);
    this.http.post('http://localhost:8091/api/v1/movies', movie, {
      headers,
      responseType: 'text' as 'json'
    })
      .subscribe(() => console.log('Movie added'));

  }


  deleteMovie(movie: any) {
    const token = localStorage.getItem('Access-token');
    const headers = { 'Authorization': `Bearer ${token}` };

    console.log(token);
    console.log('movie:', movie);
    console.log('movie.title:', movie.title);

    this.http.get<any>(`http://localhost:8091/api/v1/movies/getMovieIdByTitle?title=${encodeURIComponent(movie.title)}`, { headers })
      .subscribe(
        (dbMovie) => {
          if (!dbMovie.id) {
            console.error('Movie not found in DB');
            return;
          }

          this.http.delete(`http://localhost:8091/api/v1/movies/${dbMovie.id}`, { headers })
            .subscribe(() => {
              console.log('Movie deleted');
              this.movies = this.movies.filter(m => m.title !== movie.title);
            });
        },
        (err) => {
          console.error('Failed to fetch movie by title:', err);
        }
      );
  }


}
