package com.example.servicegenre.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class Genre {
    private String genre_name;
    private String description;
}

