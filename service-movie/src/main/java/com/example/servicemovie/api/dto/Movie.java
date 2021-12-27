package com.example.servicemovie.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public final class Movie {
    private String title;
    private String description;
    private String genres;
    private LocalDate release_date;
    private LocalTime duration;
    private String cast;
    private String type;
    private String poster_url;
    private String trailer_url;
}
