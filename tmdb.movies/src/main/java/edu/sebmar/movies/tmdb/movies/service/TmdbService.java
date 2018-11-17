package edu.sebmar.movies.tmdb.movies.service;

import edu.sebmar.movies.tmdb.movies.configuration.TmdbConfig;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class TmdbService {

    private final TmdbConfig tmdbConfig;
    private final RestTemplate restTemplate;

    public String getMovies() {

        final String urlForMovies = tmdbConfig.getUrl() + "3/discover/movie?api_key=" + tmdbConfig.getKey() + "&page=2";
        System.out.println("DEBUGUJE URL: " + urlForMovies);
        return restTemplate.exchange(urlForMovies, HttpMethod.GET, HttpEntity.EMPTY, String.class).getBody();
    }
}
