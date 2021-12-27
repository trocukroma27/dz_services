package com.example.servicemovie.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public final class Actor {
    private long id;
    private String first_name;
    private String last_name;
    private LocalDate birthday;
    private String gender;
    private String photo_url;
}
