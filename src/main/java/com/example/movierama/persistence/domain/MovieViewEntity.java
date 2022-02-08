package com.example.movierama.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Immutable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "VIEW_MOVIES")
public class MovieViewEntity {

    @Id
    private Long id;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private Long userId;

    private String userName;

    private Integer likes;

    private Integer hates;

}
