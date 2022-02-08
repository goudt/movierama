package com.example.movierama.persistence.adapter;

import com.example.movierama.movie.adapter.out.persistence.UserPort;
import com.example.movierama.persistence.UsersRepository;
import com.example.movierama.persistence.domain.UserDto;
import com.example.movierama.persistence.domain.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPort {

    private final UsersRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(UserDto user) {
        userRepo.save(new UserJpaEntity(null, user.getName(), passwordEncoder.encode(user.getPassword())));
    }

}
