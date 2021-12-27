package com.example.servicemovie.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class Genre {
    private long id;
    private String genre_name;
    private String description;
}