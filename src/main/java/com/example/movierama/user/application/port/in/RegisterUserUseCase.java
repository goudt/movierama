package com.example.movierama.user.application.port.in;

import com.example.movierama.persistence.domain.UserDto;

public interface RegisterUserUseCase {
    void register(UserDto user);
}
