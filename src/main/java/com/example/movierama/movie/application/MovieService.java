package com.example.movierama.movie.application;

import com.example.movierama.movie.adapter.out.persistence.MovieListingPort;
import com.example.movierama.movie.adapter.out.persistence.MovieRatingPort;
import com.example.movierama.movie.adapter.out.persistence.MovieSubmitPort;
import com.example.movierama.movie.application.port.in.ListMoviesUseCase;
import com.example.movierama.movie.application.port.in.RateMovieUseCase;
import com.example.movierama.movie.application.port.in.SubmitMovieUseCase;
import com.example.movierama.movie.domain.Movie;
import com.example.movierama.persistence.domain.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService implements ListMoviesUseCase, RateMovieUseCase, SubmitMovieUseCase {

    private final MovieListingPort movieListingPort;
    private final MovieRatingPort movieRatingPort;
    private final MovieSubmitPort movieSubmitPort;

    @Override
    public void rateMovie(String userName, Long movieId, MovieRatingPort.Vote vote) {
        movieRatingPort.rateMovie(movieId, userName, vote);
    }

    @Override
    public Page<Movie> listAllMovies(PageRequest page) {
        return movieListingPort.listMovies(page);
    }

    @Override
    public Page<Movie> listAllMoviesForUser(Long userId, PageRequest page) {
        return movieListingPort.listMovies(userId, page);
    }

    @Override
    public void submit(MovieDto movie) {
        movieSubmitPort.submit(movie);
    }
}
