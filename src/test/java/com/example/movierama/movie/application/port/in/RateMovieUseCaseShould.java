package com.example.movierama.movie.application.port.in;

import com.example.movierama.movie.adapter.out.persistence.MovieRatingPort;
import com.example.movierama.movie.application.MovieService;
import com.example.movierama.persistence.MoviesRepository;
import com.example.movierama.persistence.UsersRepository;
import com.example.movierama.persistence.adapter.MoviePersistenceAdapter;
import com.example.movierama.persistence.domain.MovieJpaEntity;
import com.example.movierama.persistence.domain.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RateMovieUseCaseShould {

    private RateMovieUseCase rateMovieUseCase;

    @Mock
    private MoviesRepository moviesRepo;

    @Mock
    private UsersRepository usersRepo;

    @InjectMocks
    private MoviePersistenceAdapter pesrsistenseAdaptor;

    @Test
    void failRatingOwnMovie(){
        MovieJpaEntity movieEntity = new MovieJpaEntity(0L,"movie","description", 0L, LocalDateTime.now());

        rateMovieUseCase = new MovieService(pesrsistenseAdaptor, pesrsistenseAdaptor, pesrsistenseAdaptor);
        when(moviesRepo.getById(0L)).thenReturn(movieEntity);
        when(usersRepo.findUserByName("Telly"))
                .thenReturn(Optional.of (new UserJpaEntity(0L,"Telly", null)));

        Exception expectedEx = assertThrows(ResponseStatusException.class, () ->
                rateMovieUseCase.rateMovie("Telly",0L, MovieRatingPort.Vote.LIKE));

        assertTrue(((ResponseStatusException)expectedEx).getStatus().equals(HttpStatus.BAD_REQUEST));
        assertTrue(((ResponseStatusException)expectedEx).getReason().equals("you cannot rate your own movie submission!"));


    }


}