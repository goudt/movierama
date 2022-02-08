package com.example.movierama.user.application;

import com.example.movierama.movie.adapter.out.persistence.UserPort;
import com.example.movierama.persistence.domain.UserDto;
import com.example.movierama.user.application.port.in.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService implements RegisterUserUseCase {

    private final UserPort userPort;

    @Override
    public void register(UserDto user) {
        userPort.register(user);
    }
}
