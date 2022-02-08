package com.example.movierama.movie.adapter.out.persistence;

import com.example.movierama.movie.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface MovieListingPort {

    Page<Movie> listMovies(PageRequest request);

    Page<Movie> listMovies(Long userId, PageRequest request);

}
