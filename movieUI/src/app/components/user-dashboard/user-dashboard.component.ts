// // import { Component } from '@angular/core';
// //
// // @Component({
// //   selector: 'app-user-dashboard',
// //   imports: [],
// //   templateUrl: './user-dashboard.component.html',
// //   styleUrl: './user-dashboard.component.css'
// // })
// // export class UserDashboardComponent {
// //
// // }
// //
// //
// //
// //
// //
// //
// import { Component, OnInit } from '@angular/core';
// import { MovieService } from '../../services/movie.service';
// import { HttpClient } from '@angular/common/http';
// import { CommonModule } from '@angular/common';
//
// // @Component({
// //   selector: 'app-user-dashboard',
// //   templateUrl: './user-dashboard.component.html',
// //   styleUrls: ['./user-dashboard.component.css'],
// //   imports: [CommonModule]
// // })
// @Component({
//   selector: 'app-user-dashboard',
//   standalone: true,
//   imports: [CommonModule],
//   templateUrl: './user-dashboard.component.html',
//   styleUrls: ['./user-dashboard.component.css']
// })
//
// export class UserDashboardComponent implements OnInit {
//   movies: any[] = [];
//   movieDetails: { [key: string]: any } = {};
//
//   constructor(private movieService: MovieService, private http: HttpClient) {}
//
//   ngOnInit(): void {
//     this.getMovies();  // Fetch the list of movies when the component initializes
//   }
//
//   // Fetch list of movies (replace with your actual API call if needed)
//   getMovies() {
//     this.movieService.searchMoviesByTitle('') // Empty string or set any default title
//       .subscribe({
//         next: (data) => {
//           this.movies = data;
//         },
//         error: (err) => console.error('Failed to fetch movies:', err)
//       });
//   }
//
//   // Fetch additional details for a movie by title
//   getMovieDetails(title: string) {
//     this.http.get<any>(`http://localhost:8091/api/v1/movies/${encodeURIComponent(title)}`)
//       .subscribe({
//         next: (details) => {
//           this.movieDetails[title] = details; // Store movie details by title
//         },
//         error: (err) => {
//           console.error(`Failed to fetch details for ${title}`, err);
//         }
//       });
//   }
// }
//

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
