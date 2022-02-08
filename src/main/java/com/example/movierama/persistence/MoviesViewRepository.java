package com.example.movierama.persistence;

import com.example.movierama.persistence.domain.MovieViewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MoviesViewRepository extends JpaRepository<MovieViewEntity, Long> {

    @Query("select m from MovieViewEntity m")
    Page<MovieViewEntity> getMovieList(Pageable pageable);

    @Query("select m from MovieViewEntity m where m.userId = :user_id")
    Page<MovieViewEntity> getMovieList(@Param("user_id") Long userId, Pageable pageable);

}
