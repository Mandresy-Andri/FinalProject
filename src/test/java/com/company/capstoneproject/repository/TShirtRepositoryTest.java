package com.company.capstoneproject.repository;

import com.company.capstoneproject.model.TShirt;
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
public class TShirtRepositoryTest {

    @Autowired
    TShirtRepository repo;

    @Before
    public void setUp() throws Exception {
        repo.deleteAll();
    }

    @Test
    public void addGetDeleteTShirt() {
        //add new t-shirt
        TShirt shirt = new TShirt();
        shirt.setSize("Large");
        shirt.setColor("Red");
        shirt.setDescription("Nice red shirt");
        shirt.setPrice(new BigDecimal("450.00"));
        shirt.setQuantity(5);
        repo.save(shirt);

        //get tshirt by id
        Optional<TShirt> shirtAnswer = repo.findById(shirt.getId());

        //assert that we get correct tshirt
        assertEquals(shirtAnswer.get(), shirt);

        //delete tshirt
        repo.deleteById(shirt.getId());

        //assert that tshirt was deleted
        shirtAnswer = repo.findById(shirt.getId());
        assertFalse(shirtAnswer.isPresent());
    }

    @Test
    public void getAllTShirts() {

        //add new tshirt
        TShirt shirt = new TShirt();
        shirt.setSize("Large");
        shirt.setColor("Red");
        shirt.setDescription("Nice red shirt");
        shirt.setPrice(new BigDecimal("450.00"));
        shirt.setQuantity(5);
        repo.save(shirt);

        //add new tshirt
        TShirt shirt2 = new TShirt();
        shirt2.setSize("Large");
        shirt2.setColor("Blue");
        shirt2.setDescription("Nice blue shirt");
        shirt2.setPrice(new BigDecimal("26.00"));
        shirt2.setQuantity(2);
        repo.save(shirt2);

        //get all tshirts
        List<TShirt> shirtList = repo.findAll();

        //assert all tshirts were retrieved
        assertEquals(shirtList.size(), 2);

    }

    @Test
    public void getRedShirt() {
        //add new tshirt
        TShirt shirt = new TShirt();
        shirt.setSize("Large");
        shirt.setColor("Red");
        shirt.setDescription("Nice red shirt");
        shirt.setPrice(new BigDecimal("450.00"));
        shirt.setQuantity(5);
        repo.save(shirt);

        //get all tshirts
        Optional<List<TShirt>> shirtList = repo.findByColor("Red");

        //assert all tshirts were retrieved
        assertEquals(shirtList.get().get(0), shirt);
    }

    @Test
    public void getLargeShirt() {
        //add new tshirt
        TShirt shirt = new TShirt();
        shirt.setSize("Large");
        shirt.setColor("Red");
        shirt.setDescription("Nice red shirt");
        shirt.setPrice(new BigDecimal("450.00"));
        shirt.setQuantity(5);
        repo.save(shirt);

        //get all tshirts
        Optional<List<TShirt>> shirtList = repo.findBySize("Large");

        //assert all tshirts were retrieved
        assertEquals(shirtList.get().get(0), shirt);
    }

    @Test
    public void updateTShirt() {

        //add new author
        TShirt shirt = new TShirt();
        shirt.setSize("Large");
        shirt.setColor("Red");
        shirt.setDescription("Nice red shirt");
        shirt.setPrice(new BigDecimal("450.00"));
        shirt.setQuantity(5);
        repo.save(shirt);

        //update size and color
        shirt.setSize("Humongous");
        shirt.setColor("Magenta");
        repo.save(shirt);

        //assert that tshirt was updated
        Optional<TShirt> shirtAnswer = repo.findById(shirt.getId());
        assertEquals(shirtAnswer.get(), shirt);
    }
}
