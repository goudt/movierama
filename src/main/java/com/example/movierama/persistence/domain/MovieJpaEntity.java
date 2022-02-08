package com.example.movierama.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String title;

    private String description;

    private Long userId;

    private LocalDateTime createdAt;

    public MovieJpaEntity(String title, String description, Long userId) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }
}
