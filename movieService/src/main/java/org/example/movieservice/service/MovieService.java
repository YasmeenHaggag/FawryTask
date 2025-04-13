package org.example.movieservice.service;

import org.example.movieservice.entity.Movie;
import org.example.movieservice.dto.MovieResponseDTO;
import org.example.movieservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
//@RequiredArgsConstructor
public class MovieService {

    private final RestTemplate restTemplate;
    @Autowired
    private MovieRepository movieRepository;

    @Value("${omdb.api.key}")
    private String API_KEY;

    @Value("${omdb.api.url}")
    private String omdbApiUrl;

    public MovieService() {
        this.restTemplate = new RestTemplate();
    }


    public List<MovieResponseDTO> searchMoviesByTitle(String title) {
        String searchUrl = String.format("%s?s=%s&apikey=%s", omdbApiUrl, title, API_KEY);
        ResponseEntity<Map<String, Object>> searchResponse = restTemplate.exchange(
                searchUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        Map<String, Object> searchBody = searchResponse.getBody();
        if (searchBody != null && "True".equals(searchBody.get("Response"))) {
            List<Map<String, Object>> searchResults = (List<Map<String, Object>>) searchBody.get("Search");
            List<MovieResponseDTO> movies = new ArrayList<>();

            for (Map<String, Object> result : searchResults) {
                String imdbID = (String) result.get("imdbID");

                String detailsUrl = String.format("%s?i=%s&apikey=%s", omdbApiUrl, imdbID, API_KEY);
                ResponseEntity<Map<String, Object>> detailResponse = restTemplate.exchange(
                        detailsUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

                Map<String, Object> details = detailResponse.getBody();
                if (details != null && "True".equals(details.get("Response"))) {
                    MovieResponseDTO dto = new MovieResponseDTO();
                    dto.setTitle((String) details.get("Title"));
                    dto.setYear((String) details.get("Year"));
                    dto.setType((String) details.get("Type"));
                    dto.setActors((String) details.get("Actors"));
                    dto.setAwards((String) details.get("Awards"));
                    movies.add(dto);
                }
            }
            return movies;
        } else {
            throw new RuntimeException("No movie found or invalid response");
        }
    }





    public List<Movie> getAll(){
       return movieRepository.findAll();
    }


    public Movie findByTitle(String title) {
        return movieRepository.getMovieByTitle(title);
    }

    public int getMovieIdByTitle(String title){
        return movieRepository.getMovieByTitle(title).getId();
    }
}
