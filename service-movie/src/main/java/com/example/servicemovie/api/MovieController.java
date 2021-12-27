package com.example.servicemovie.api;


import com.example.servicemovie.api.dto.MovieOut;
import com.example.servicemovie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies")
public final class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieOut>> index() {
        final List<MovieOut> movies = movieService.fetchAll();

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieOut> show(@PathVariable long id) {
        try{
            final MovieOut movie = movieService.fetchById(id);

            return ResponseEntity.ok(movie);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.example.servicemovie.api.dto.Movie movie) {
        final long id = movieService.create(movie);
        final String location = String.format("/movies/" + id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody com.example.servicemovie.api.dto.Movie movie){
        try{
            movieService.update(id, movie);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        movieService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
