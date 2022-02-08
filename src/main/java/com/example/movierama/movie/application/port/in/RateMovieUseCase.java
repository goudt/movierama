package com.example.movierama.movie.application.port.in;

import com.example.movierama.movie.adapter.out.persistence.MovieRatingPort;

public interface RateMovieUseCase {
    void rateMovie(String userName, Long movieId, MovieRatingPort.Vote vote);
}
