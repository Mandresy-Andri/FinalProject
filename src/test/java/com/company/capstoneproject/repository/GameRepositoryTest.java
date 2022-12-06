package com.company.capstoneproject.repository;
import com.company.capstoneproject.model.Game;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameRepositoryTest {

    @Autowired
    GameRepository gameRepository;

    @Before
    public void setUp() throws Exception {
        gameRepository.deleteAll();
    }

    // add/create, read, delete
    @Test
    public void addGetDeleteGame() {

        Game game = new Game();
        game.setTitle("Castlevania");
        game.setEsrbRating("E");
        game.setDescription("Enter At Your Own Risk!\nBecause when you finally meet the count, you know he'll be going " +
                "for the jugular. So keep your stake sharp.");
        // From the back of the box of Castlevania on NES
        game.setPrice(BigDecimal.valueOf(29.99));
        game.setStudio("Konami");
        game.setQuantity(500);

        // Add
        gameRepository.save(game);

        // Get
        Optional<Game> gameCopy = gameRepository.findById(game.getGame_id());
        assertEquals(gameCopy.get(), game);

        // Delete
        gameRepository.deleteById(game.getGame_id());
        gameCopy = gameRepository.findById(game.getGame_id());
        assertFalse(gameCopy.isPresent());

    }

    // read all
    @Test
    public void getAllGames() {

        Game game1 = new Game();
        game1.setTitle("Castlevania");
        game1.setEsrbRating("E");
        game1.setDescription("Enter At Your Own Risk!\nBecause when you finally meet the count, you know he'll be going " +
                "for the jugular. So keep your stake sharp.");
        // From the back of the box of Castlevania on NES
        game1.setPrice(BigDecimal.valueOf(29.99));
        game1.setStudio("Konami");
        game1.setQuantity(500);

        gameRepository.save(game1);

        Game game2 = new Game();
        game2.setTitle("Super Mario 64");
        game2.setEsrbRating("E");
        game2.setDescription("Mario is super in a whole new way! Retrieve the Power Stars from their hidden locations " +
                "and confront your arch nemesis - Bowser, King of the Koopas!");
        // From the back of the box of Super Mario 64 on N64
        game2.setPrice(BigDecimal.valueOf(39.97));
        game2.setStudio("Nintendo");
        game2.setQuantity(500);

        gameRepository.save(game2);

        Game game3 = new Game();
        game3.setTitle("Mega Man X");
        game3.setEsrbRating("E");
        game3.setDescription("The future lies on the brink of destruction and a new Mega Man must emerge to face Sigma " +
                "and his forces before the human race is wiped from the planet!");
        // From the back of the box of Mega Man X on SNES
        game3.setPrice(BigDecimal.valueOf(34.99));
        game3.setStudio("Capcom");
        game3.setQuantity(500);

        gameRepository.save(game3);

        List<Game> allGames = gameRepository.findAll();
        assertEquals(allGames.size(), 3);

    }

    // get game by studio
    @Test
    public void getGamesByStudio() {

        Game game = new Game();
        game.setTitle("Castlevania");
        game.setEsrbRating("E");
        game.setDescription("Enter At Your Own Risk!\nBecause when you finally meet the count, you know he'll be going " +
                "for the jugular. So keep your stake sharp.");
        // From the back of the box of Castlevania on NES
        game.setPrice(BigDecimal.valueOf(29.99));
        game.setStudio("Konami");
        game.setQuantity(500);

        gameRepository.save(game);

        Game game1 = new Game();
        game1.setTitle("Castlevania: Symphony of the Night");
        game1.setEsrbRating("T");
        game1.setDescription("The legacy of evil returns...\nAs a descendant of Dracula, you must end the vampire " +
                "bloodline. Hidden weapons, secrets and characters... the largest Castlevania ever!");
        // From the back of the box of Castlevania Symphony of the Night on PS1
        game1.setPrice(BigDecimal.valueOf(64.99));
        game1.setStudio("Konami");
        game1.setQuantity(500);

        gameRepository.save(game1);

        // Get by studio
        Optional<List<Game>> castlevanias = gameRepository.getGamesByStudio("Konami");

        assertEquals(castlevanias.get().get(0).getStudio(), game.getStudio());

    }

    // get game by esrb rating
    @Test
    public void getGamesByEsrbRating() {

        Game game = new Game();
        game.setTitle("Castlevania: Symphony of the Night");
        game.setEsrbRating("T");
        game.setDescription("The legacy of evil returns...\nAs a descendant of Dracula, you must end the vampire " +
                "bloodline. Hidden weapons, secrets and characters... the largest Castlevania ever!");
        // From the back of the box of Symphony of the Night on PS1
        game.setPrice(BigDecimal.valueOf(64.99));
        game.setStudio("Konami");
        game.setQuantity(500);

        gameRepository.save(game);

        game = new Game();
        game.setTitle("Dragon Ball Z: Budokai 3");
        game.setEsrbRating("T");
        game.setDescription("The Greatest Warriors. A Tournament of Champions.\nBattle with any one of up to 40 " +
                "characters from Dragon Ball Z, DBZ movies, and Dragon Ball GT\nOnly ONE Will Prevail!");
        // From the back fo the box of Dragon Ball Z Budokai 3 on PS2
        game.setPrice(BigDecimal.valueOf(24.99));
        game.setStudio("Dimps");
        game.setQuantity(500);

        gameRepository.save(game);

        // Get by ESRB Rating
        Optional<List<Game>> teen_ratings = gameRepository.getGamesByEsrbRating("T");

        assertEquals(teen_ratings.get().get(0).getEsrbRating(), game.getEsrbRating());

    }

    // get game by title
    @Test
    public void getGamesByTitle() {

        Game game1 = new Game();
        game1.setTitle("Castlevania");
        game1.setEsrbRating("E");
        game1.setDescription("Enter At Your Own Risk!\nBecause when you finally meet the count, you know he'll be going " +
                "for the jugular. So keep your stake sharp.");
        // From the back of the box of Castlevania on NES
        game1.setPrice(BigDecimal.valueOf(29.99));
        game1.setStudio("Konami");
        game1.setQuantity(500);

        gameRepository.save(game1);

        Game game2 = new Game();
        game2.setTitle("Super Mario 64");
        game2.setEsrbRating("E");
        game2.setDescription("Mario is super in a whole new way! Retrieve the Power Stars from their hidden locations " +
                "and confront your arch nemesis - Bowser, King of the Koopas!");
        // From the back of the box of Super Mario 64 on N64
        game2.setPrice(BigDecimal.valueOf(39.97));
        game2.setStudio("Nintendo");
        game2.setQuantity(500);

        gameRepository.save(game2);

        Game game3 = new Game();
        game3.setTitle("Castlevania");
        game3.setEsrbRating("E");
        game3.setDescription("Enter At Your Own Risk!\nBecause when you finally meet the count, you know he'll be going " +
                "for the jugular. So keep your stake sharp.");
        // From the back of the box of Castlevania on NES
        game3.setPrice(BigDecimal.valueOf(29.99));
        game3.setStudio("Konami");
        game3.setQuantity(500);

        gameRepository.save(game3);

        // Get by title
        Optional<List<Game>> castlevanias = gameRepository.getGamesByTitle("Castlevania");

        assertEquals(castlevanias.get().get(0).getTitle(), game1.getTitle());

    }

    // update
    @Test
    public void updateGame() {

        Game game1 = new Game();
        game1.setTitle("Castlevania");
        game1.setEsrbRating("E");
        game1.setDescription("Enter At Your Own Risk!\nBecause when you finally meet the count, you know he'll be going " +
                "for the jugular. So keep your stake sharp.");
        // From the back of the box of Castlevania on NES
        game1.setPrice(BigDecimal.valueOf(29.99));
        game1.setStudio("Konami");
        game1.setQuantity(500);

        gameRepository.save(game1);

        // UPDATE
        game1.setTitle("Akumajou Dracula");
        gameRepository.save(game1);

        Optional<Game> theGame = gameRepository.findById(game1.getGame_id());
        assertEquals(theGame.get(), game1);

    }

}
