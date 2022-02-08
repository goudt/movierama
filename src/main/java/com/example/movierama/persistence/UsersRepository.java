package com.example.movierama.persistence;

import com.example.movierama.persistence.domain.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UserJpaEntity, Long> {

    Optional<UserJpaEntity> findUserByNameAndPassword(String name, String password);

    Optional<UserJpaEntity> findUserByName(String name);

}
