package com.example.movierama.persistence;

import com.example.movierama.persistence.domain.MovieJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviesRepository extends JpaRepository<MovieJpaEntity, Long> {

//    List<MovieEntity> findAllByUserIdOrderByCreatedAt(Long userId);
//
//    List<MovieEntity> findAllOrderByCreatedAt();

}
