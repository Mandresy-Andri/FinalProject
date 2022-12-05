package com.company.capstoneproject.controller;

import com.company.capstoneproject.model.TShirt;
import com.company.capstoneproject.repository.TShirtRepository;
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
@WebMvcTest(TShirtController.class)
public class TShirtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TShirtRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private TShirt shirt;
    private String shirtJson;
    private List<TShirt> allAuthors = new ArrayList<>();
    private String allShirtsJson;

    @Before
    public void setup() throws Exception {
        //add new author
        shirt = new TShirt();
        shirt.setId(1);
        shirt.setSize("Large");
        shirt.setColor("Red");
        shirt.setDescription("Nice red shirt");
        shirt.setPrice(new BigDecimal("450"));
        shirt.setQuantity(5);

        shirtJson = mapper.writeValueAsString(shirt);

        //add new author
        TShirt shirt2 = new TShirt();
        shirt2.setId(2);
        shirt2.setSize("Large");
        shirt2.setColor("Blue");
        shirt2.setDescription("Nice blue shirt");
        shirt2.setPrice(new BigDecimal("26"));
        shirt2.setQuantity(2);

        //add authors to list
        allAuthors.add(shirt);
        allAuthors.add(shirt2);

        //change list to JSON
        allShirtsJson = mapper.writeValueAsString(allAuthors);
    }


    // Testing GET all
    @Test
    public void shouldReturnAllTShirt() throws Exception {
        doReturn(allAuthors).when(repo).findAll();

        mockMvc.perform(
                        get("/tshirt"))
                .andExpect(status().isOk())
                .andExpect(content().json(allShirtsJson)
                );
    }

    // Testing GET by id
    @Test
    public void shouldReturnTShirtById() throws Exception {
        doReturn(Optional.of(shirt)).when(repo).findById(1);

        ResultActions result = mockMvc.perform(
                        get("/tshirt/{id}", 1))
                .andExpect(status().isOk())
                .andExpect((content().json(shirtJson))
                );
    }

    // Testing GET by color
    @Test
    public void shouldReturnTShirtByColor() throws Exception {
        List<TShirt> shirtList = new ArrayList<>();
        shirtList.add(shirt);
        String allRedShirtsJson = mapper.writeValueAsString(shirtList);

        doReturn(Optional.of(shirtList)).when(repo).findByColor("Red");

        ResultActions result = mockMvc.perform(
                        get("/tshirt/color/{color}", "Red"))
                .andExpect(status().isOk())
                .andExpect((content().json(allRedShirtsJson))
                );
    }

    // Testing GET by size
    @Test
    public void shouldReturnTShirtBySize() throws Exception {
        List<TShirt> shirtList = new ArrayList<>();
        shirtList.add(shirt);
        String allLargeShirtsJson = mapper.writeValueAsString(shirtList);

        doReturn(Optional.of(shirtList)).when(repo).findBySize("Large");

        ResultActions result = mockMvc.perform(
                        get("/tshirt/size/{size}", "Large"))
                .andExpect(status().isOk())
                .andExpect((content().json(allLargeShirtsJson))
                );
    }

    //Testing POST
    @Test
    public void shouldCreateTShirtOnPostRequest() throws Exception {
        //create new author
        TShirt inputTShirt = new TShirt();
        inputTShirt.setSize("Large");
        inputTShirt.setColor("Red");
        inputTShirt.setDescription("Nice red shirt");
        inputTShirt.setPrice(new BigDecimal("450"));
        inputTShirt.setQuantity(5);

        //change to Json input to test Post
        String inputJson = mapper.writeValueAsString(inputTShirt);

        //It should return shirt when repo saves inputShirt
        doReturn(shirt).when(repo).save(inputTShirt);

        //perform post
        mockMvc.perform(
                        post("/tshirt")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(shirtJson));

    }

    //Testing PUT
    @Test
    public void shouldUpdate() throws Exception {
        doReturn(shirt).when(repo).save(shirt);
        mockMvc.perform(
                        put("/tshirt")
                                .content(shirtJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    //Testing DELETE
    @Test
    public void shouldDeleteById() throws Exception {
        doNothing().when(repo).deleteById(shirt.getId());
        mockMvc.perform(delete("/tshirt/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
