package com.example.movierama.movie.adapter.in.web;

import com.example.movierama.movie.adapter.out.persistence.MovieRatingPort;
import com.example.movierama.movie.application.port.in.ListMoviesUseCase;
import com.example.movierama.movie.application.port.in.RateMovieUseCase;
import com.example.movierama.movie.application.port.in.SubmitMovieUseCase;
import com.example.movierama.movie.domain.Movie;
import com.example.movierama.persistence.domain.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final ListMoviesUseCase listMoviesUseCase;

    private final RateMovieUseCase rateMoviesUseCase;

    private final SubmitMovieUseCase submitMovieUseCase;

    public void rateMovie(Long movieId, String userName, MovieRatingPort.Vote vote) {
        rateMoviesUseCase.rateMovie(userName, movieId, vote);
    }

    public Page<Movie> listMovies(int page, int pageSize, String orderBy) {
//        Sort sort = getOrderParams(orderBy);
        Sort sort = Sort.by(Movie.Sorting.By.valueOfLabel(orderBy).name());
        return listMoviesUseCase.listAllMovies(PageRequest.of(page, pageSize, sort));
    }

    public Page<Movie> listMoviesByOwner(Long ownerId, int page, int pageSize, String orderBy) {
//        Sort sort = getOrderParams(orderBy);
        Sort sort = Sort.by(Movie.Sorting.By.valueOfLabel(orderBy).name());
        return listMoviesUseCase.listAllMoviesForUser(ownerId, PageRequest.of(page, pageSize, sort));
    }

    private Sort getOrderParams(String orderBy) {
        List<String> orderByList = List.of(Movie.Sorting.By.valueOfLabel(orderBy).name());
        if (!Movie.Sorting.By.createdAt.equals(Movie.Sorting.By.valueOfLabel(orderBy)))
            orderByList.add(Movie.Sorting.By.createdAt.name());
        return Sort.by(orderByList.stream().map((o) -> new Sort.Order(Sort.Direction.DESC, o)).collect(Collectors.toList()));
    }

    public void submitMovie(MovieDto movie) {
        submitMovieUseCase.submit(movie);
    }


}
