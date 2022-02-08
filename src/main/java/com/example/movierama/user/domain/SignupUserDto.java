package com.example.movierama.user.domain;

import com.example.movierama.persistence.domain.UserDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SignupUserDto extends UserDto {
    @NotEmpty(message = "password cannot be empty")
    @Size(min = 4, max = 25)
    private String passwordRetype;
}
