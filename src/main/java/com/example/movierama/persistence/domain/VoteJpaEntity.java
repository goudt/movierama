package com.example.movierama.persistence.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "votes")
@Data
@NoArgsConstructor
public class VoteJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private Long user;

    private Long movie;

    private Integer rating;

    public VoteJpaEntity(Long user, Long movie, Integer rating) {
        this.user = user;
        this.movie = movie;
        this.rating = rating;
    }
}
