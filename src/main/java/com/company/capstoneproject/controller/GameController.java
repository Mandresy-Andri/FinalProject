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
    @GetMapping(value = "/{id}")
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

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGame(@RequestBody @Valid Game game) {
        repo.save(game);
    }

    // Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable int id) {
        repo.deleteById(id);
    }

    @GetMapping(value = "/studio/{studio}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGamesByStudio(@PathVariable String studio) {
        Optional<List<Game>> returnVal = repo.getGamesByStudio(studio);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    @GetMapping(value = "/esrb_rating/{esrb_rating}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGamesByEsrb_rating(@PathVariable String esrb_rating) {
        Optional<List<Game>> returnVal = repo.getGamesByEsrbRating(esrb_rating);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    @GetMapping(value = "/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGamesByTitle(@PathVariable String title) {
        Optional<List<Game>> returnVal = repo.getGamesByTitle(title);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }
}
