package com.company.capstoneproject.repository;

import com.company.capstoneproject.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    Optional<List<Game>> getGamesByStudio(String studio);
    Optional<List<Game>> getGamesByEsrbRating(String esrb_rating);
    Optional<List<Game>> getGamesByTitle(String title);
}
