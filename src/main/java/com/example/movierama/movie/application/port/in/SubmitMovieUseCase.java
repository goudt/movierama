package com.example.movierama.movie.application.port.in;

import com.example.movierama.persistence.domain.MovieDto;

public interface SubmitMovieUseCase {
    void submit(MovieDto movie);
}
