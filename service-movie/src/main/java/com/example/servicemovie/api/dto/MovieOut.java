package com.example.servicemovie.api.dto;

import com.example.servicemovie.repo.model.Movie;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MovieOut {
    private Movie movie;
    private List<Genre> genres;
    private List<Actor> actors;

    public MovieOut(Movie movie, List<Genre> genreList, List<Actor> actorList) {
        this.movie = movie;
        this.genres = genreList;
        this.actors = actorList;
    }
}
