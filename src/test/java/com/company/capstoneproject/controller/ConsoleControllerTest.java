package com.company.capstoneproject.controller;

import com.company.capstoneproject.model.Console;
import com.company.capstoneproject.repository.ConsoleRepository;
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
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@WebMvcTest(ConsoleController.class)
public class ConsoleControllerTest {
    @MockBean
    private ConsoleRepository repository;
    @Autowired
    private MockMvc mockMvc;
    Console console1;
    Console console2;
    List<Console> consoles = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();
    @Before
    public void setUp() {
        console1 = new Console();
        console1.setConsole_id(1);
        console1.setQuantity(43);
        console1.setManufacturer("Sony");
        console1.setPrice(new BigDecimal(434));
        console2 = new Console();
        console2.setConsole_id(2);
        console2.setQuantity(43);
        console2.setManufacturer("Microsoft");
        console2.setPrice(new BigDecimal(384));
        consoles.add(console1);
        consoles.add(console2);
    }
    //wrong url used
    @Test
    public void findByIdShouldReturnOkResponseAndConsole() throws Exception {
//        ARRANGE
        int id = 1;
        doReturn(Optional.of(console1)).when(repository).findById(1);
//        ACT & ASSERT
        mockMvc.perform(
                        get("/console/{id}",1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    //Wrong url. Didn't specify which manufacturer it should look for
    @Test
    public void findByManufacturerShouldReturnOkResponseAndConsoleWithManufacturer() throws Exception {
//        ARRANGE
        String manufacturer = console2.getManufacturer();
        List<Console> expectedConsoleResult = Collections.singletonList(console2);
        when(repository.getConsoleByManufacturer(manufacturer)).thenReturn(expectedConsoleResult);
        String expectedResultJson = objectMapper.writeValueAsString(expectedConsoleResult);
//        ACT & ASSERT
        MvcResult result = mockMvc.perform(
                        get( "/manufacturer/{manufacturer}","Microsoft"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResultJson))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void findAllConsolesShouldReturnOKResponseAndListOfConsoles() throws Exception {
        doReturn(consoles).when(repository).findAll();
        mockMvc.perform(get( "/console"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void shouldCreatedNewConsoleOnPostRequest() throws Exception {
        Console  input = new Console();
        input.setConsole_id(1);
        input.setQuantity(43);
        input.setManufacturer("Sony");
        input.setPrice(new BigDecimal(434));
        String inputJson = objectMapper.writeValueAsString(input);
        doReturn(console1).when(repository).save(input);
        mockMvc.perform(
                        post( "/console")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    //wrong url
    //Error in ConsoleController. The put mapping is returning ACCEPTED instead of NO CONTENT
    @Test
    public void updateConsoleShouldReturnAcceptedResponse() throws Exception {
        console1.setManufacturer("Nintendo");
        String updatedConsoleJson = objectMapper.writeValueAsString(console1);
        mockMvc.perform(
                        put("/console/1").content(updatedConsoleJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    //No mock object in method (doReturn...)
    //Wrong url (/console/console/{id}
    @Test
    public void deleteConsoleShouldReturnNoContentResponse() throws Exception {
        doNothing().when(repository).deleteById(console1.getConsole_id());
        mockMvc.perform(
                        delete("/console/{id}",1))
                .andExpect(status().isNoContent());
    }
}