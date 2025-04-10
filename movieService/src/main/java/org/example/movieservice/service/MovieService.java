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

    public List<MovieResponseDTO> searchMovie(String title) {
        String url = String.format("%s?s=%s&apikey=%s", omdbApiUrl, title, API_KEY);
//        http://www.omdbapi.com/?t=Inception&y=2010
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );

        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null && "True".equals(responseBody.get("Response"))) {
            List<Map<String, Object>> searchResults =
                    (List<Map<String, Object>>) responseBody.get("Search");
            List<MovieResponseDTO> movies = new ArrayList<>();
            for (Map<String, Object> result : searchResults) {
                MovieResponseDTO dto = new MovieResponseDTO();
                dto.setTitle((String) result.get("Title"));
                dto.setYear((String) result.get("Year"));
                dto.setActors((String) result.get("actors"));
                dto.setAwards((String) result.get("awards"));
                movies.add(dto);
            }
            return movies;
            } else{
                throw new RuntimeException("No movie found or invalid response");
            }
        }



    public List<Movie> getAll(){
       return movieRepository.findAll();
    }


    public Movie findByTitle(String title) {
        return movieRepository.getMovieByTitle(title);
    }
}
