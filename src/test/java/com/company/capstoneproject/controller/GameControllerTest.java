package com.company.capstoneproject.controller;

import com.company.capstoneproject.model.Game;
import com.company.capstoneproject.repository.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private Game game;
    private String gameJson;
    private List<Game> allGames = new ArrayList<>();
    private String allGamesJson;

    @Before
    public void setup() throws Exception {
        //add new author
        game = new Game();
        game.setTitle("Pokemon");
        game.setEsrbRating("E");
        game.setDescription("Pokémon introduces legions of gamers to the world of Kanto, where the likes of " +
                "Charmander, Pikachu, and Mewtwo were first discovered. Through exciting exploration, battles, " +
                "and trades, Trainers are able to access 150 Pokémon.\n" +
                "You begin your journey in Pallet Town as a young boy. After a dangerous brush with wild Pokémon, " +
                "Professor Oak teaches you how to capture Pokémon, and then sends you on your way as a fledgling " +
                "Trainer. During your journey through Kanto, you must capture Pokémon to record their information in " +
                "your Pokédex, as well as become a better Trainer by competing in Gyms scattered throughout the region. " +
                "Once you've proven your mettle as a Pokémon Trainer, it's time to take on the Elite Four, a crack group " +
                "of Trainers that will put all of your learned skills to the test.\n" +
                "Your journey will be far from easy. In addition to the many Trainers and wild Pokémon you'll encounter " +
                "along the way, you'll also have to be watchful of Team Rocket, a despicable group of Pokémon thieves. " +
                "Prevent Team Rocket from stealing rare Pokémon and stop their criminal ways!\n" +
                "You won't be able to catch every Pokémon in either Pokémon Red or Pokémon Blue; to collect every " +
                "Pokémon, you'll have to trade with friends via the Game Link™ Cable. With it, you can also take your " +
                "team of faithful Pokémon into battle against your pals to see how well your team stacks up!\n" +
                "There's much to see and do in Pokémon Red and Pokémon Blue. Start your journey through Kanto and " +
                "become a Master Trainer!");
        // From game description on the official Pokemon website
        game.setPrice(BigDecimal.valueOf(44.99));
        game.setStudio("Game Freak");
        game.setQuantity(800);

        gameJson = mapper.writeValueAsString(game);

        //add new game
        Game game1 = new Game();
        game1.setTitle("Pokemon Blue");
        game1.setEsrbRating("E");
        game1.setDescription("Pokémon Blue introduces legions of gamers to the world of Kanto, where the likes of " +
                "Charmander, Pikachu, and Mewtwo were first discovered. Through exciting exploration, battles, " +
                "and trades, Trainers are able to access 150 Pokémon.\n" +
                "You begin your journey in Pallet Town as a young boy. After a dangerous brush with wild Pokémon, " +
                "Professor Oak teaches you how to capture Pokémon, and then sends you on your way as a fledgling " +
                "Trainer. During your journey through Kanto, you must capture Pokémon to record their information in " +
                "your Pokédex, as well as become a better Trainer by competing in Gyms scattered throughout the region. " +
                "Once you've proven your mettle as a Pokémon Trainer, it's time to take on the Elite Four, a crack group " +
                "of Trainers that will put all of your learned skills to the test.\n" +
                "Your journey will be far from easy. In addition to the many Trainers and wild Pokémon you'll encounter " +
                "along the way, you'll also have to be watchful of Team Rocket, a despicable group of Pokémon thieves. " +
                "Prevent Team Rocket from stealing rare Pokémon and stop their criminal ways!\n" +
                "You won't be able to catch every Pokémon in either Pokémon Red or Pokémon Blue; to collect every " +
                "Pokémon, you'll have to trade with friends via the Game Link™ Cable. With it, you can also take your " +
                "team of faithful Pokémon into battle against your pals to see how well your team stacks up!\n" +
                "There's much to see and do in Pokémon Red and Pokémon Blue. Start your journey through Kanto and " +
                "become a Master Trainer!");
        // From game description on the official Pokemon website
        game1.setPrice(BigDecimal.valueOf(44.99));
        game1.setStudio("Game Freak");
        game1.setQuantity(800);

        //add new game
        Game game2 = new Game();
        game1.setTitle("Pokemon Red");
        game1.setEsrbRating("E");
        game1.setDescription("Pokémon Red introduces legions of gamers to the world of Kanto, where the likes of " +
                "Charmander, Pikachu, and Mewtwo were first discovered. Through exciting exploration, battles, " +
                "and trades, Trainers are able to access 150 Pokémon.\n" +
                "You begin your journey in Pallet Town as a young boy. After a dangerous brush with wild Pokémon, " +
                "Professor Oak teaches you how to capture Pokémon, and then sends you on your way as a fledgling " +
                "Trainer. During your journey through Kanto, you must capture Pokémon to record their information in " +
                "your Pokédex, as well as become a better Trainer by competing in Gyms scattered throughout the region. " +
                "Once you've proven your mettle as a Pokémon Trainer, it's time to take on the Elite Four, a crack group " +
                "of Trainers that will put all of your learned skills to the test.\n" +
                "Your journey will be far from easy. In addition to the many Trainers and wild Pokémon you'll encounter " +
                "along the way, you'll also have to be watchful of Team Rocket, a despicable group of Pokémon thieves. " +
                "Prevent Team Rocket from stealing rare Pokémon and stop their criminal ways!\n" +
                "You won't be able to catch every Pokémon in either Pokémon Red or Pokémon Blue; to collect every " +
                "Pokémon, you'll have to trade with friends via the Game Link™ Cable. With it, you can also take your " +
                "team of faithful Pokémon into battle against your pals to see how well your team stacks up!\n" +
                "There's much to see and do in Pokémon Red and Pokémon Blue. Start your journey through Kanto and " +
                "become a Master Trainer!");
        // From game description on the official Pokemon website
        game1.setPrice(BigDecimal.valueOf(44.99));
        game1.setStudio("Game Freak");
        game1.setQuantity(800);

        //add games to list
        allGames.add(game);
        allGames.add(game1);
        allGames.add(game2);

        //change list to JSON
        allGamesJson = mapper.writeValueAsString(allGames);
    }


    // Testing GET all
    @Test
    public void shouldReturnAllGames() throws Exception {
        doReturn(allGames).when(repo).findAll();

        mockMvc.perform(
                        get("/game"))
                .andExpect(status().isOk())
                .andExpect(content().json(allGamesJson)
                );
    }

    // Testing GET by id
    @Test
    public void shouldReturnGameById() throws Exception {
        doReturn(Optional.of(game)).when(repo).findById(1);

        ResultActions result = mockMvc.perform(
                        get("/game/{id}", 1))
                .andExpect(status().isOk())
                .andExpect((content().json(gameJson))
                );
    }

    // Testing GET by studio
    @Test
    public void shouldReturnGameByStudio() throws Exception {
        List<Game> gameList = new ArrayList<>();
        gameList.add(game);
        String allGameFreakGamesJson = mapper.writeValueAsString(gameList);

        doReturn(Optional.of(gameList)).when(repo).getGamesByStudio("Game Freak");

        ResultActions result = mockMvc.perform(
                        get("/game/studio/{studio}", "Game Freak"))
                .andExpect(status().isOk())
                .andExpect((content().json(allGameFreakGamesJson))
                );
    }

    // Testing GET by esrb rating
    @Test
    public void shouldReturnGameByEsrbRating() throws Exception {
        List<Game> gameList = new ArrayList<>();
        gameList.add(game);
        String allRatedEGamesJson = mapper.writeValueAsString(gameList);

        doReturn(Optional.of(gameList)).when(repo).getGamesByEsrbRating("E");

        ResultActions result = mockMvc.perform(
                        get("/game/Esrb_rating/{Esrb_rating}", "E"))
                .andExpect(status().isOk())
                .andExpect((content().json(allRatedEGamesJson))
                );
    }

    // Testing GET by title
    @Test
    public void shouldReturnGameByTitle() throws Exception {
        List<Game> gameList = new ArrayList<>();
        gameList.add(game);
        String allPokemonGamesJson = mapper.writeValueAsString(gameList);

        doReturn(Optional.of(gameList)).when(repo).getGamesByTitle("Pokemon");

        ResultActions result = mockMvc.perform(
                        get("/game/title/{title}", "Pokemon"))
                .andExpect(status().isOk())
                .andExpect((content().json(allPokemonGamesJson))
                );
    }

    //Testing POST
    @Test
    public void shouldCreateGameOnPostRequest() throws Exception {
        //create new author
        Game inputGame = new Game();
        inputGame.setTitle("Pokemon Yellow");
        inputGame.setEsrbRating("E");
        inputGame.setDescription("Building on the terrific success of Pokémon Red Version and Pokémon Blue Version, " +
                "Pokémon Yellow Version returns Trainers to Kanto for more even more fun and adventure. Pokémon Yellow " +
                "delivers a feature that hasn't been duplicated in any other Pokémon game-Pikachu actually follows you " +
                "around throughout your journey!\n" +
                "The graphics for Pokémon Yellow are updated slightly from Pokémon Red and Pokémon Blue, and you can " +
                "use innovative peripherals such as the Game Boy Printer, which allow you to print out stickers of " +
                "your favorite Pokémon. Pokémon Yellow also introduces challenges and battle modes that let you " +
                "compete in exciting ways.");
        // From game description on the official Pokemon website
        inputGame.setPrice(BigDecimal.valueOf(44.99));
        inputGame.setQuantity(600);

        //change to Json input to test Post
        String inputJson = mapper.writeValueAsString(inputGame);

        //It should return game when repo saves inputGame
        doReturn(game).when(repo).save(inputGame);

        //perform post
        mockMvc.perform(
                        post("/game")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(gameJson));

    }

    //Testing PUT
    @Test
    public void shouldUpdate() throws Exception {
        doReturn(game).when(repo).save(game);
        mockMvc.perform(
                        put("/game")
                                .content(gameJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    //Testing DELETE
    @Test
    public void shouldDeleteById() throws Exception {
        doNothing().when(repo).deleteById(game.getGame_id());
        mockMvc.perform(delete("/game/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
