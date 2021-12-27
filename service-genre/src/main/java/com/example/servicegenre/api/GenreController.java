package com.example.servicegenre.api;

import com.example.servicegenre.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<List<com.example.servicegenre.repo.model.Genre>> index() {
        final List<com.example.servicegenre.repo.model.Genre> genres = genreService.fetchAll();

        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.servicegenre.repo.model.Genre> show(@PathVariable long id) {
        try{
            final com.example.servicegenre.repo.model.Genre genre = genreService.fetchById(id);

            return ResponseEntity.ok(genre);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.example.servicegenre.api.dto.Genre genre) {
        final String genre_name = genre.getGenre_name();
        final String description = genre.getDescription();
        final long id = genreService.create(genre_name, description);
        final String location = String.format("/genres/" + id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody com.example.servicegenre.api.dto.Genre genre){
        final String genre_name = genre.getGenre_name();
        final String description = genre.getDescription();

        try{
            genreService.update(id, genre_name, description);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        genreService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
