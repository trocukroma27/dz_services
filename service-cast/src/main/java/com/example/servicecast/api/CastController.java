package com.example.servicecast.api;


import com.example.servicecast.service.CastService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cast")
public class CastController {

    private final CastService castService;

    @GetMapping
    public ResponseEntity<List<com.example.servicecast.repo.model.Actor>> index() {
        final List<com.example.servicecast.repo.model.Actor> actors = castService.fetchAll();

        return ResponseEntity.ok(actors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.servicecast.repo.model.Actor> show(@PathVariable long id) {
        try{
            final com.example.servicecast.repo.model.Actor actor = castService.fetchById(id);

            return ResponseEntity.ok(actor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.example.servicecast.api.dto.Actor actor) {
        final long id = castService.create(actor);
        final String location = String.format("/cast/" + id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody com.example.servicecast.api.dto.Actor actor){
        try{
            castService.update(id, actor);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        castService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
