package edu.sebmar.movies.tmdb.movies.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(){
        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
