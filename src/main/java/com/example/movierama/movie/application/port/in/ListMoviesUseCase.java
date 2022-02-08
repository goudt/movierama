package com.example.movierama.movie.application.port.in;

import com.example.movierama.movie.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ListMoviesUseCase {
    Page<Movie> listAllMovies(PageRequest page);

    Page<Movie> listAllMoviesForUser(Long ownerId, PageRequest page);
}
