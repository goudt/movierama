package com.example.movierama.persistence.adapter;

import com.example.movierama.movie.adapter.out.persistence.MovieListingPort;
import com.example.movierama.movie.adapter.out.persistence.MovieRatingPort;
import com.example.movierama.movie.adapter.out.persistence.MovieSubmitPort;
import com.example.movierama.movie.domain.Movie;
import com.example.movierama.persistence.MoviesRepository;
import com.example.movierama.persistence.MoviesViewRepository;
import com.example.movierama.persistence.UsersRepository;
import com.example.movierama.persistence.VotesRepository;
import com.example.movierama.persistence.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MoviePersistenceAdapter implements MovieListingPort, MovieRatingPort, MovieSubmitPort {

    private final MoviesRepository moviesRepo;

    private final MoviesViewRepository moviesViewRepo;

    private final VotesRepository votesRepo;

    private final UsersRepository usersRepo;

    public MovieJpaEntity submitMovie(MovieDto dto) {
        MovieJpaEntity entity = new MovieJpaEntity(dto.getTitle(), dto.getDescription(),
                usersRepo.findUserByName(dto.getUser())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "unable to find user: " + dto.getUser()))
                        .getId());
        return moviesRepo.save(entity);
    }

    private String convertCreatedAt(LocalDateTime date) {
        String createdAt = " ";
        final long days = date.until(LocalDateTime.now(), ChronoUnit.DAYS);
        final long hours = date.until(LocalDateTime.now(), ChronoUnit.HOURS);
        final long minutes = date.until(LocalDateTime.now(), ChronoUnit.MINUTES);
        if (days > 0) {
            createdAt = days + " days";
        } else if (hours > 0) {
            createdAt = hours + " hours";
        } else {
            createdAt = minutes + " minutes";
        }
        return createdAt;
    }

    @Override
    public void rateMovie(Long movieId, String userName, Vote vote) {
        final MovieJpaEntity movie = moviesRepo.getById(movieId);
        final Optional<UserJpaEntity> user = usersRepo.findUserByName(userName);
        final Long userId = user.orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "unable to find user: " + userName))
                .getId();

        if (movie == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unable to find the movie");
        } else {
            if (movie.getUserId().equals(userId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you cannot rate your own movie submission!");
            }
        }

        Optional<VoteJpaEntity> existingVote = votesRepo.findByMovieAndUser(movieId, userId);
        if (existingVote.isPresent()) {
            //user already voted this movie
            final VoteJpaEntity existingVoteJpaEntity = existingVote.get();
            if (Vote.valueOfInt(existingVoteJpaEntity.getRating()).equals(vote)) {
                //attempting to vote with same rating - vote will be retracted
                votesRepo.delete(existingVoteJpaEntity);
            } else {
                //switching vote
                switchVote(movieId, userId, vote, existingVoteJpaEntity);
            }
        } else {
            votesRepo.save(new VoteJpaEntity(userId, movieId, vote.getIntVal()));
        }

    }

    @Transactional
    void switchVote(Long movieId, Long userId, Vote vote, VoteJpaEntity existingVoteJpaEntity) {
        votesRepo.delete(existingVoteJpaEntity);
        votesRepo.save(new VoteJpaEntity(userId, movieId, vote.getIntVal()));
    }

    @Override
    public void submit(MovieDto movie) {
        final Long ownerId = usersRepo.findUserByName(movie.getUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "unable to find user: " + movie.getUser()))
                .getId();
        moviesRepo.save(new MovieJpaEntity(movie.getTitle(), movie.getDescription(), ownerId));
    }

    @Override
    public Page<Movie> listMovies(PageRequest request) {
        Page<MovieViewEntity> movieViewEntities = moviesViewRepo
                .getMovieList(PageRequest.of(request.getPageNumber(), request.getPageSize()
                        , request.getSort().descending()));
        return new PageImpl(getMovieEntities(movieViewEntities),
                PageRequest.of(request.getPageNumber(), request.getPageSize(), request.getSort().descending()),
                movieViewEntities.getTotalElements());
    }

    @Override
    public Page<Movie> listMovies(Long userId, PageRequest request) {
        Page<MovieViewEntity> movieViewEntities = moviesViewRepo
                .getMovieList(userId, PageRequest.of(request.getPageNumber(), request.getPageSize()
                        , request.getSort().descending()));
        return new PageImpl(getMovieEntities(movieViewEntities),
                PageRequest.of(request.getPageNumber(), request.getPageSize(), request.getSort().descending()),
                movieViewEntities.getTotalElements());
    }

    private List<Movie> getMovieEntities(Page<MovieViewEntity> movieViewEntities) {
        return movieViewEntities.getContent().stream().map(m -> Movie.builder()
                .id(m.getId())
                .title(m.getTitle())
                .description(m.getDescription())
                .owner(m.getUserName())
                .ownerId(m.getUserId())
                .hates(m.getHates())
                .likes(m.getLikes())
                .publicationDate(convertCreatedAt(m.getCreatedAt()))
                .build()).collect(Collectors.toList());
    }
}

