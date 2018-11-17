package edu.sebmar.movies.tmdb.movies.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "tmdb")
public class TmdbConfig {

    @Getter
    @Setter
    private String key;

    @Getter
    @Setter
    private String url;
}
