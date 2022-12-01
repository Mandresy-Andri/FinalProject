package com.company.capstoneproject.controller;

import com.company.capstoneproject.model.Game;
import com.company.capstoneproject.repository.GameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/games")
public class GameController {

    @Autowired
    private GameRepository repo;

    // Create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Game addGame(@RequestBody @Valid Game game) {
        return repo.save(game);
    }

    // Read
    @GetMapping(value = "/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Game getGameById(@PathVariable int id) {
        Optional<Game> returnVal = repo.findById(id);
        return returnVal.orElse(null);
    }

    // Read all
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGames() {
        return repo.findAll();
    }

    @PutMapping(value = "/id/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Game updateGame(@RequestBody Game game, @PathVariable int id) {
        Game newGame = repo.findById(id)
                .map(g -> {
                    g.setTitle(game.getTitle());
                    g.setEsrbRating(game.getEsrbRating());
                    g.setDescription(game.getDescription());
                    g.setPrice(game.getPrice());
                    g.setStudio(game.getStudio());
                    g.setQuantity(game.getQuantity());
                    return repo.save(game);
                }) .orElseGet(() -> {
                    game.setGame_id(id);
                    return repo.save(game);
                });

        return repo.save(game);
    }

    // Delete
    @DeleteMapping("/game/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable int id) {
        repo.deleteById(id);
    }

    @GetMapping(value = "/studio/{studio}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> findByStudio(@PathVariable String studio) {
        return repo.getGamesByStudio(studio);
    }

    @GetMapping(value = "/Esrb_rating/{Esrb_rating}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> findByEsrb_rating(@PathVariable String esrb_rating) {
        return repo.getGamesByEsrbRating(esrb_rating);
    }

    @GetMapping(value = "/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> findByTitle(@PathVariable String title) {
        return repo.getGamesByTitle(title);
    }
}
