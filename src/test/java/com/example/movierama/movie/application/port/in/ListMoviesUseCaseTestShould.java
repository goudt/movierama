package com.example.movierama.movie.application.port.in;


import com.example.movierama.movie.application.MovieService;
import com.example.movierama.movie.domain.Movie;
import com.example.movierama.persistence.MoviesViewRepository;
import com.example.movierama.persistence.adapter.MoviePersistenceAdapter;
import com.example.movierama.persistence.domain.MovieViewEntity;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ListMoviesUseCaseTestShould {

    private ListMoviesUseCase listMoviesUseCase;

    @Mock
    private MoviesViewRepository moviesViewRepo;

    @InjectMocks
    private MoviePersistenceAdapter pesrsistenseAdaptor;

    List<MovieViewEntity> movieViewEntites = List.of(
            new MovieViewEntity(3L,"movie7","description7", LocalDateTime.now(), 2L,"user2", 3, 2),
            new MovieViewEntity(1L,"movie6","description6", LocalDateTime.now(), 1L,"user1", 1, 1),
            new MovieViewEntity(2L,"movie4","description4", LocalDateTime.now(), 0L,"user0", 0, 1),
            new MovieViewEntity(0L,"movie5","description5", LocalDateTime.now(), 1L,"user1", 0, 2)
            );

    @Test
    void succeedListingMoviesSorted() {
        listMoviesUseCase = new MovieService(pesrsistenseAdaptor, pesrsistenseAdaptor, pesrsistenseAdaptor);

        when(moviesViewRepo.getMovieList(PageRequest.of(0, 5, Sort.by("likes").descending())))
                .thenReturn(new PageImpl<MovieViewEntity>(movieViewEntites));

        final Page<Movie> movies = listMoviesUseCase.listAllMovies(PageRequest.of(0,5, Sort.by("likes")));
        assertThat(movies).isNotEmpty();
        assertThat(movies.getTotalPages()).isEqualTo(1);
        assertThat(movies.getNumber()).isEqualTo(0);
        assertThat(movies.getContent().get(0).getId()).isEqualTo(3L);
        assertThat(movies.getContent().get(1).getId()).isEqualTo(1L);
    }

}