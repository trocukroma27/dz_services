package com.example.servicecast.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public final class Actor {
    private String first_name;
    private String last_name;
    private LocalDate birthday;
    private String gender;
    private String photo_url;
}

