package com.example.movierama.persistence;

import com.example.movierama.persistence.domain.VoteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotesRepository extends JpaRepository<VoteJpaEntity, Long> {
    Optional<VoteJpaEntity> findByMovieAndUser(Long movieId, Long userId);
}
