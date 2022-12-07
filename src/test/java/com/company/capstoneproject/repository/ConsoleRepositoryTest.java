package com.company.capstoneproject.repository;
import com.company.capstoneproject.model.Console;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConsoleRepositoryTest {

    @Autowired
    ConsoleRepository repo;

    @Before
    public void setUp() throws Exception{
        repo.deleteAll();
    }

    @Test
    public void addGetDeleteConsole(){
        Console console = new Console();
        console.setManufacturer("Nintendo");
        console.setDescription("Switch");
        console.setPrice(new BigDecimal("299.00"));
        console.setQuantity(2);
        repo.save(console);

        Optional<Console> consoleAnswer = repo.findById(console.getConsole_id());

        assertEquals(consoleAnswer.get(), console);

        repo.deleteById(console.getConsole_id());

        consoleAnswer = repo.findById(console.getConsole_id());
        assertFalse(consoleAnswer.isPresent());
    }

    @Test
    public void getAllConsoles(){

        Console console = new Console();
        console.setManufacturer("Sony");
        console.setDescription("PS5");
        console.setPrice(new BigDecimal("499.00"));
        console.setQuantity(2);
        repo.save(console);

        Console console2 = new Console();
        console2.setManufacturer("Microsoft");
        console2.setDescription("XBOX 360");
        console2.setPrice(new BigDecimal("199.00"));
        console2.setQuantity(1);
        repo.save(console2);

        List<Console> consoleList = repo.findAll();

        assertEquals(consoleList.size(), 2);
    }

    @Test
    public void getPS2() {
        Console console = new Console();
        console.setManufacturer("Sony");
        console.setDescription("PS2");
        console.setPrice(new BigDecimal(129.00));
        console.setQuantity(1);

        Optional<List<Console>> consoleList = repo.findByDescription("PS2");

        assertEquals(consoleList.get().get(0), console);

    }

    @Test
    public void updateConsole() {
        Console console = new Console();
        console.setManufacturer("Sony");
        console.setDescription("PSP");
        console.setPrice(new BigDecimal("112.00"));
        console.setQuantity(3);

        console.setDescription("PSVita");
        repo.save(console);

        Optional<Console> consoleAnswer = repo.findById(console.getConsole_id());
        assertEquals(consoleAnswer.get(), console);
    }

}
