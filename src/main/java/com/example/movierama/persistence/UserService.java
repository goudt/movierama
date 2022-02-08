package com.example.movierama.persistence;

import com.example.movierama.persistence.domain.UserDto;
import com.example.movierama.persistence.domain.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    public UserJpaEntity createNewUser(UserDto dto) {
        return userRepo.save(new UserJpaEntity(null, dto.getName(), passwordEncoder.encode(dto.getPassword())));
    }


}
