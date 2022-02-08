package com.example.movierama.movie.adapter.out.persistence;

import lombok.Getter;

public interface MovieRatingPort {

    void rateMovie(Long movieId, String userName, Vote vote);

    enum Vote {
        LIKE(1), HATE(0);

        @Getter
        int intVal;

        Vote(int vote) {
            intVal = vote;
        }

        public static Vote valueOfInt(int intVal) {
            for (Vote rate : values()) {
                if (rate.intVal == intVal) return rate;
            }
            return null;
        }
    }
}
