package com.example.movierama.movie.adapter.out.persistence;

import com.example.movierama.persistence.domain.UserDto;

public interface UserPort {

    void register(UserDto user);

}
