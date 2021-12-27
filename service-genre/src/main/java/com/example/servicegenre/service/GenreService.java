package com.example.servicegenre.service;

import com.example.servicegenre.repo.GenreRepo;
import com.example.servicegenre.repo.model.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreService {

    private final GenreRepo genreRepo;

    public List<Genre> fetchAll(){
        return genreRepo.findAll();
    }

    public Genre fetchById(long id) throws IllegalArgumentException{
        final Optional<Genre> maybeGenre = genreRepo.findById(id);

        if(maybeGenre.isPresent())
            return maybeGenre.get();
        else
            throw new IllegalArgumentException("Invalid movie ID");
    }

    public long create(String genre_name, String description){
        final Genre newGenre = new Genre(genre_name, description);
        final Genre savedMovie = genreRepo.save(newGenre);

        return savedMovie.getId();
    }

    public void update(long id, String genre_name, String description) throws IllegalArgumentException{
        final Optional<Genre> maybeGenre = genreRepo.findById(id);

        if (maybeGenre.isPresent()){
            final Genre existedGenre = maybeGenre.get();
            if (genre_name != null && !genre_name.isBlank()) existedGenre.setGenre_name(genre_name);
            if (description != null && !description.isBlank()) existedGenre.setDescription(description);

            genreRepo.save(existedGenre);
        } else{
            throw new IllegalArgumentException("Invalid car ID");
        }
    }

    public void delete(long id){
        genreRepo.deleteById(id);
    }
}
