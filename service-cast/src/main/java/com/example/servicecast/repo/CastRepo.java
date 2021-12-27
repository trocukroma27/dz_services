package com.example.servicecast.repo;

import com.example.servicecast.repo.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CastRepo extends JpaRepository<Actor, Long> {
}
