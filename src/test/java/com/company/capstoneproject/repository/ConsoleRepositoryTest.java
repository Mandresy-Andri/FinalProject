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
        console.setPrice(new BigDecimal("299.00"));
        console.setQuantity(2);
        console.setModel("Switch");
        console.setMemory_amount("Memory");
        console.setProcessor("Processor");
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
        console.setPrice(new BigDecimal("499.00"));
        console.setQuantity(2);
        console.setModel("PSP");
        console.setMemory_amount("Memory");
        console.setProcessor("Processor");
        repo.save(console);

        Console console2 = new Console();
        console2.setManufacturer("Microsoft");
        console2.setPrice(new BigDecimal("199.00"));
        console2.setQuantity(1);
        console2.setModel("XBox360");
        console2.setMemory_amount("Memory");
        console2.setProcessor("Processor");
        repo.save(console2);

        List<Console> consoleList = repo.findAll();

        assertEquals(consoleList.size(), 2);
    }

    @Test
    public void updateConsole() {
        Console console = new Console();
        console.setManufacturer("Sony");
        console.setPrice(new BigDecimal("112.00"));
        console.setModel("PS2");
        console.setMemory_amount("Memory");
        console.setProcessor("Processor");
        console.setQuantity(3);

        repo.save(console);

        Optional<Console> consoleAnswer = repo.findById(console.getConsole_id());
        assertEquals(consoleAnswer.get(), console);
    }

}
