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
        game.setGame_id(1);
        game.setTitle("Pokemon");
        game.setEsrbRating("E");
        game.setDescription("Pokémon introduces legions of gamers to the world of Kanto, where the likes of " +
                "Charmander, Pikachu, and Mewtwo were first discovered.");
        // From game description on the official Pokemon website
        game.setPrice(BigDecimal.valueOf(44.99));
        game.setStudio("Game Freak");
        game.setQuantity(800);

        gameJson = mapper.writeValueAsString(game);

        //add new game
        Game game1 = new Game();
        game1.setGame_id(2);
        game1.setTitle("Pokemon Blue");
        game1.setEsrbRating("E");
        game1.setDescription("Pokémon Blue introduces legions of gamers to the world of Kanto, where the likes of " +
                "Charmander, Pikachu, and Mewtwo were first discovered.");
        // From game description on the official Pokemon website
        game1.setPrice(BigDecimal.valueOf(44.99));
        game1.setStudio("Game Freak");
        game1.setQuantity(800);

        //add new game
        Game game2 = new Game();
        game2.setGame_id(3);
        game2.setTitle("Pokemon Red");
        game2.setEsrbRating("E");
        game2.setDescription("Pokémon Red introduces legions of gamers to the world of Kanto, where the likes of " +
                "Charmander, Pikachu, and Mewtwo were first discovered.");
        // From game description on the official Pokemon website
        game2.setPrice(BigDecimal.valueOf(44.99));
        game2.setStudio("Game Freak");
        game2.setQuantity(800);

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
                        get("/games"))
                .andExpect(status().isOk())
                .andExpect(content().json(allGamesJson)
                );
    }

    // Testing GET by id
    @Test
    public void shouldReturnGameById() throws Exception {
        doReturn(Optional.of(game)).when(repo).findById(1);

        ResultActions result = mockMvc.perform(
                        get("/games/{id}", 1))
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
                        get("/games/studio/{studio}", "Game Freak"))
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
                        get("/games/esrb_rating/{esrb_rating}", "E"))
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
                        get("/games/title/{title}", "Pokemon"))
                .andExpect(status().isOk())
                .andExpect((content().json(allPokemonGamesJson))
                );
    }

    //Testing POST
    @Test
    public void shouldCreateGameOnPostRequest() throws Exception {
        //create new author
        Game inputGame = new Game();
        inputGame.setGame_id(4);
        inputGame.setTitle("Pokemon Yellow");
        inputGame.setEsrbRating("E");
        inputGame.setDescription("Pokémon Yellow Version returns Trainers to Kanto for more even more fun and adventure.");
        // From game description on the official Pokemon website
        inputGame.setPrice(BigDecimal.valueOf(44.99));
        inputGame.setStudio("Game Freak");
        inputGame.setQuantity(600);

        //change to Json input to test Post
        String inputJson = mapper.writeValueAsString(inputGame);

        //It should return game when repo saves inputGame
        doReturn(game).when(repo).save(inputGame);

        //perform post
        mockMvc.perform(
                        post("/games")
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
                        put("/games")
                                .content(gameJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    //Testing DELETE
    @Test
    public void shouldDeleteById() throws Exception {
        doNothing().when(repo).deleteById(game.getGame_id());
        mockMvc.perform(delete("/games/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
