package edu.sebmar.movies.tmdb.movies.controller;

import edu.sebmar.movies.tmdb.movies.service.TmdbService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/movies")
@AllArgsConstructor
public class GetMovies {

    private final TmdbService tmdbService;

    @GetMapping
    public String getMovies() {
        return tmdbService.getMovies();
    }
}
