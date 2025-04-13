

import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule, JsonPipe } from '@angular/common';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css'],
  standalone: true,
  imports: [CommonModule, JsonPipe]
})
export class UserDashboardComponent implements OnInit {
  movies: any[] = [];
  movieDetails: { [title: string]: any } = {};

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadAllMovies();
  }

  loadAllMovies() {
    this.http.get<any[]>('http://localhost:8091/api/v1/movies')
      .subscribe({
        next: (data) => {
          this.movies = data;
        },
        error: (err) => {
          console.error('Failed to fetch all movies:', err);
        }
      });
  }

  getMovieDetails(title: string) {
    this.http.get<any>(`http://localhost:8091/api/v1/movies/${encodeURIComponent(title)}`)
      .subscribe({
        next: (details) => {
          this.movieDetails[title] = details;
        },
        error: (err) => {
          console.error(`Failed to fetch details for ${title}`, err);
        }
      });
  }
}
