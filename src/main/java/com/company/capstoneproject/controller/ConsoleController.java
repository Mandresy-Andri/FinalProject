package com.company.capstoneproject.controller;

import com.company.capstoneproject.model.Console;
import com.company.capstoneproject.repository.ConsoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/console")
public class ConsoleController {

    @Autowired
    private ConsoleRepository repo;

    //Create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Console addConsole(@RequestBody Console console){
        return repo.save(console);
    }

    @GetMapping(value = "/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Console getConsoleById(@PathVariable int id){
        Optional<Console> returnVal = repo.findById(id);
        return returnVal.orElse(null);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Console> getConsole(){
        return repo.findAll();
    }

    @PutMapping(value = "/id/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Console updateConsole(@RequestBody Console console, @PathVariable int id){
        Console newConsole = repo.findById(id).map(c ->{
            //c.setConsole_name(console.getConsole_name());
            c.setDescription(console.getDescription());
            c.setManufacturer(console.getManufacturer());
            c.setPrice(console.getPrice());
            c.setQuantity(console.getQuantity());
            return repo.save(console);
        }) .orElseGet(() -> {
            console.setConsole_id(id);
            return repo.save(console);
        });
        return repo.save(console);
    }

    @DeleteMapping("/console/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsole(@PathVariable int id){
        repo.deleteById(id);
    }
    @GetMapping(value = "/manufacturer/{manufacturer}")
    @ResponseStatus(HttpStatus.OK)
    public List<Console> findByManufacturer(@PathVariable String manufacturer){
        return repo.getConsoleByManufacturer(manufacturer);
    }
}
