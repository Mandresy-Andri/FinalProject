package com.company.capstoneproject.repository;

import com.company.capstoneproject.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> getGamesByStudio(String studio);
    List<Game> getGamesByEsrbRating(String esrb_rating);
    List<Game> getGamesByTitle(String title);
}
