package org.example.movieservice.repository;

import org.example.movieservice.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movie,Integer> {
    Movie findByTitleIgnoreCase(String title);
    Movie getMovieByTitle (String title);
}
