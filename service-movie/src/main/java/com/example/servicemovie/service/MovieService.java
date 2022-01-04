package com.example.servicemovie.service;

import com.example.servicemovie.api.dto.Actor;
import com.example.servicemovie.api.dto.Genre;
import com.example.servicemovie.api.dto.MovieOut;
import com.example.servicemovie.repo.MovieRepo;
import com.example.servicemovie.repo.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class MovieService {

    private final MovieRepo movieRepo;

    public List<MovieOut> fetchAll() {
        List<Movie> movies = movieRepo.findAll();

        List<MovieOut> moviesOut = new ArrayList<>();
        List<Genre> genreList;
        List<Actor> actorsList;
        for (Movie movie : movies) {
            RestTemplate restTemplate = new RestTemplate();

            String cast = movie.getCast();
            String[] actorsIdArr = cast.split(",");
            actorsList = new ArrayList<>();
            for (String actorId : actorsIdArr) {
                Actor actor = restTemplate.getForObject("http://192.168.49.2:31002/cast/" + actorId, Actor.class);
                actorsList.add(actor);
            }

            String genres = movie.getGenres();
            String[] genreIdArr = genres.split(",");
            genreList = new ArrayList<>();
            for (String genreId : genreIdArr) {
                Genre genre = restTemplate.getForObject("http://192.168.49.2:31001/genres/" + genreId, Genre.class);
                genreList.add(genre);
            }

            MovieOut movieOut = new MovieOut(movie, genreList, actorsList);
            moviesOut.add(movieOut);
        }
        return moviesOut;
    }

    public MovieOut fetchById(long id) throws IllegalArgumentException{
        final Optional<Movie> maybeMovie = movieRepo.findById(id);


        Movie movie;
        if(maybeMovie.isPresent()){
            movie = maybeMovie.get();
            RestTemplate restTemplate = new RestTemplate();

            String cast = movie.getCast();
            String[] actorsIdArr = cast.split(",");
            List<Actor> actorsList = new ArrayList<>();
            for (String actorId : actorsIdArr) {
                Actor actor = restTemplate.getForObject("http://192.168.49.2:31002/cast/" + actorId, Actor.class);
                actorsList.add(actor);
            }

            String genres = movie.getGenres();
            String[] genreIdArr = genres.split(",");
            List<Genre> genreList = new ArrayList<>();
            for (String genreId : genreIdArr) {
                Genre genre = restTemplate.getForObject("http://192.168.49.2:31001/genres/" + genreId, Genre.class);
                genreList.add(genre);
            }

            MovieOut movieOut = new MovieOut(movie, genreList, actorsList);

            return movieOut;
        } else {
            throw new IllegalArgumentException("Invalid movie ID");
        }
    }

    public long create(com.example.servicemovie.api.dto.Movie movie){
        final String title = movie.getTitle();
        final String description = movie.getDescription();
        final String genres = movie.getGenres();
        final LocalDate release_date = movie.getRelease_date();
        final LocalTime duration = movie.getDuration();
        final String cast = movie.getCast();
        final String type = movie.getType();
        final String poster_url = movie.getPoster_url();
        final String trailer_url = movie.getTrailer_url();

        final Movie newMovie = new Movie(title, description, genres, release_date, duration, cast, type, poster_url, trailer_url);
        final Movie savedMovie = movieRepo.save(newMovie);

        return savedMovie.getId();
    }

    public void update(long id, com.example.servicemovie.api.dto.Movie movie) throws IllegalArgumentException{
        final Optional<Movie> maybeMovie = movieRepo.findById(id);

        final String title = movie.getTitle();
        final String description = movie.getDescription();
        final String genres = movie.getGenres();
        final LocalDate release_date = movie.getRelease_date();
        final LocalTime duration = movie.getDuration();
        final String cast = movie.getCast();
        final String type = movie.getType();
        final String poster_url = movie.getPoster_url();
        final String trailer_url = movie.getTrailer_url();

        if (maybeMovie.isPresent()){
            final Movie existedMovie = maybeMovie.get();
            if (title != null && !title.isBlank()) existedMovie.setTitle(title);
            if (description != null && !description.isBlank()) existedMovie.setDescription(description);
            if (genres != null && !genres.isBlank()) existedMovie.setGenres(genres);
            if (release_date != null) existedMovie.setRelease_date(release_date);
            if (duration != null && !duration.equals(LocalTime.of(0,0,0))) existedMovie.setDuration(duration);
            if (cast != null && !cast.isBlank()) existedMovie.setCast(cast);
            if (type != null && !type.isBlank()) existedMovie.setType(type);
            if (poster_url != null && !poster_url.isBlank()) existedMovie.setPoster_url(poster_url);
            if (trailer_url != null && !trailer_url.isBlank()) existedMovie.setTrailer_url(trailer_url);

            movieRepo.save(existedMovie);
        } else{
            throw new IllegalArgumentException("Invalid car ID");
        }
    }

    public void delete(long id){
        movieRepo.deleteById(id);
    }
}
