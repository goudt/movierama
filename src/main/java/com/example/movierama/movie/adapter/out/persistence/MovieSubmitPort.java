package com.example.movierama.movie.adapter.out.persistence;

import com.example.movierama.persistence.domain.MovieDto;

public interface MovieSubmitPort {
    void submit(MovieDto movie);
}
