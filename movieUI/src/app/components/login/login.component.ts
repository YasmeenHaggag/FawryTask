
import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import {CommonModule} from '@angular/common';
import { Movie } from '../../models/movie.model';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';
  movieTitles: string[] = [];
  movieDetails: { [title: string]: any } = {};

  constructor(private http: HttpClient, private router: Router) {
  }

  onLogin() {
    const loginData = {
      username: this.username,
      password: this.password
    };

    this.http.post<any>('http://localhost:8090/api/auth/login', loginData)
      .subscribe({
        next: (response) => {
          const token = response['Access-token'];
          if (token) {
            localStorage.setItem('Access-token', token);
            this.http.get('http://localhost:8090/api/auth/extract-role?token=' + token, { responseType: 'text' })
              .subscribe({
                next: (userRole: string) => {
                  console.log('User role:', userRole);

                  if (userRole === 'ROLE_ADMIN') {
                    this.router.navigate(['/admin-dashboard']);
                  } else {
                    this.router.navigate(['/user-dashboard']);
                  }
                },
                error: (err) => {
                  console.error('Failed to get user role', err);
                  this.errorMessage = 'Failed to get user role.';
                }
              });
          }
        },
        error: () => {
          this.errorMessage = 'Invalid username or password.';
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

