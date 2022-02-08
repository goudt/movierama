package com.example.movierama.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotEmpty(message = "user name cannot be empty")
    @Size(min = 4, max = 100)
    private String name;

    @NotEmpty(message = "password cannot be empty")
    @Size(min = 4, max = 25)
    private String password;
}
