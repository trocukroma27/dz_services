package com.example.servicecast.service;

import com.example.servicecast.repo.CastRepo;
import com.example.servicecast.repo.model.Actor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CastService {

    private final CastRepo castRepo;

    public List<Actor> fetchAll(){
        return castRepo.findAll();
    }

    public Actor fetchById(long id) throws IllegalArgumentException{
        final Optional<Actor> maybeActor = castRepo.findById(id);

        if(maybeActor.isPresent())
            return maybeActor.get();
        else
            throw new IllegalArgumentException("Invalid movie ID");
    }

    public long create(com.example.servicecast.api.dto.Actor actor){
        final String first_name = actor.getFirst_name();
        final String last_name = actor.getLast_name();
        final LocalDate birthday = actor.getBirthday();
        final String gender = actor.getGender();
        final String photo_url = actor.getPhoto_url();

        final Actor newActor = new Actor(first_name, last_name, birthday, gender, photo_url);
        final Actor savedActor = castRepo.save(newActor);

        return savedActor.getId();
    }

    public void update(long id, com.example.servicecast.api.dto.Actor actor) throws IllegalArgumentException{
        final Optional<Actor> maybeActor = castRepo.findById(id);

        final String first_name = actor.getFirst_name();
        final String last_name = actor.getLast_name();
        final LocalDate birthday = actor.getBirthday();
        final String gender = actor.getGender();
        final String photo_url = actor.getPhoto_url();

        if (maybeActor.isPresent()){
            final Actor existedActor = maybeActor.get();
            if (first_name != null && !first_name.isBlank()) existedActor.setFirst_name(first_name);
            if (last_name != null && !last_name.isBlank()) existedActor.setLast_name(last_name);
            if (birthday != null) existedActor.setBirthday(birthday);
            if (gender != null && !gender.isBlank()) existedActor.setGender(gender);
            if (photo_url != null && !photo_url.isBlank()) existedActor.setPhoto_url(photo_url);

            castRepo.save(existedActor);
        } else{
            throw new IllegalArgumentException("Invalid car ID");
        }
    }

    public void delete(long id){
        castRepo.deleteById(id);
    }
}
