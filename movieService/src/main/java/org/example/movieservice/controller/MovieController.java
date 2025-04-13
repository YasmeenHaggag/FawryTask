package org.example.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.movieservice.entity.Movie;
import org.example.movieservice.dto.MovieResponseDTO;
import org.example.movieservice.repository.MovieRepository;
import org.example.movieservice.service.AuthClient;
import org.example.movieservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    @Autowired
    MovieService movieService;
    @Autowired
    AuthClient authClient;
    @Autowired
    MovieRepository movieRepository;


    @GetMapping()
    public ResponseEntity<List<Movie>> getAll(){
        List<Movie> movieList = movieService.getAll();
            return ResponseEntity.ok(movieList);
    }


    @GetMapping("/search")
    public ResponseEntity<List<MovieResponseDTO>> searchMovie(@RequestParam String title){
        List<MovieResponseDTO >response = movieService.searchMoviesByTitle(title);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<String> addMovie(@RequestHeader("Authorization")
                                               String token, @RequestBody Movie movie) {
        if (!authClient.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized");
        }

        movieRepository.save(movie);
        return ResponseEntity.ok("Movie added");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@RequestHeader("Authorization")
                                String token, @PathVariable int id){
        if (!authClient.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized");
        }

        movieRepository.deleteById(id);
        return ResponseEntity.ok("Movie deleted");
    }



    @GetMapping("/{title}")
    public ResponseEntity<Movie> getByTitle(@PathVariable String title) {
        Movie movies = movieService.findByTitle(title);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/getMovieIdByTitle")
    public int getMovieIdByTitle(@RequestParam String title){
        return movieService.getMovieIdByTitle(title);
    }

}
