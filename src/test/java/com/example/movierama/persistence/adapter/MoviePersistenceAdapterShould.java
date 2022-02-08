package com.example.movierama.persistence.adapter;

import com.example.movierama.movie.adapter.out.persistence.MovieListingPort;
import com.example.movierama.movie.adapter.out.persistence.MovieRatingPort;
import com.example.movierama.movie.adapter.out.persistence.MovieSubmitPort;
import com.example.movierama.movie.application.port.in.ListMoviesUseCase;
import com.example.movierama.movie.domain.Movie;
import com.example.movierama.persistence.MoviesRepository;
import com.example.movierama.persistence.MoviesViewRepository;
import com.example.movierama.persistence.UsersRepository;
import com.example.movierama.persistence.VotesRepository;
import com.example.movierama.persistence.domain.MovieViewEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class MoviePersistenceAdapterShould {

    @Mock
    private MoviesViewRepository moviesViewRepo;
    @Mock
    private MoviesRepository moviesRepo;
    @Mock
    private VotesRepository votesRepo;
    @Mock
    private UsersRepository usersRepo;
    @InjectMocks
    private MoviePersistenceAdapter movieListingPort;

    final List<MovieViewEntity> movieViewEntites = List.of(
            new MovieViewEntity(0L,"movie4","description4", LocalDateTime.now(), 0L,"user0", 0, 1),
            new MovieViewEntity(0L,"movie5","description5", LocalDateTime.now(), 1L,"user1", 0, 2),
            new MovieViewEntity(0L,"movie6","description6", LocalDateTime.now(), 1L,"user1", 1, 1),
            new MovieViewEntity(0L,"movie7","description7", LocalDateTime.now(), 2L,"user2", 3, 2)

    );

    @Test
    void listMovies() {
        final PageImpl<MovieViewEntity> pageResult = new PageImpl<>(movieViewEntites);
        when(moviesViewRepo.getMovieList(PageRequest.of(0,5)))
                .thenReturn(pageResult);
        Page<MovieViewEntity>  c = moviesViewRepo.getMovieList(PageRequest.of(0,5));
        Page<Movie> page = movieListingPort.listMovies(PageRequest.of(0,5, Sort.by("likes")));
        assert(page!=null);
    }
}