package com.example.movierama.movie.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode
public class Movie {
    private Long id;
    private String title;
    private String description;
    private String publicationDate;
    private String owner;
    private Long ownerId;
    private Integer likes;
    private Integer hates;

    public static class Sorting {
        public enum By {
            likes("likes"), hates("hates"), createdAt("date");

            public final String label;

            By(String by) {
                label = by;
            }

            public static By valueOfLabel(String label) {
                for (By by : By.values()) {
                    if (by.label.equalsIgnoreCase(label))
                        return by;
                }
                return null;
            }


        }
    }
}
