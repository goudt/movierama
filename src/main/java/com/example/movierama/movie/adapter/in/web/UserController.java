package com.example.movierama.movie.adapter.in.web;

import com.example.movierama.persistence.domain.UserDto;
import com.example.movierama.user.application.port.in.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;

    public void registerUser(UserDto user) {
        registerUserUseCase.register(user);
    }

}
