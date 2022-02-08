package com.example.movierama.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    private String user;

}
