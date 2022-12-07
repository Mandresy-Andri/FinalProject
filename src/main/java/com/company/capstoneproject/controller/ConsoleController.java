package com.company.capstoneproject.controller;

import com.company.capstoneproject.model.Console;
import com.company.capstoneproject.repository.ConsoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ConsoleController {

    @Autowired
    private ConsoleRepository repo;

    //Create
    @PostMapping(value = "/console")
    @ResponseStatus(HttpStatus.CREATED)
    public Console addConsole(@RequestBody Console console){
        return repo.save(console);
    }

    @GetMapping(value ="/console/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Console getConsoleById(@PathVariable int id){
        Optional<Console> returnVal = repo.findById(id);
        return returnVal.orElse(null);
    }

    @GetMapping(value = "/console")
    @ResponseStatus(HttpStatus.OK)
    public List<Console> getConsole(){
        return repo.findAll();
    }

    @PutMapping(value = "/console/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Console updateConsole(@RequestBody @Valid Console console, @PathVariable int id){
        Console newConsole = repo.findById(id).map(c ->{
            c.setManufacturer(console.getManufacturer());
            c.setPrice(console.getPrice());
            c.setQuantity(console.getQuantity());
            c.setModel(console.getModel());
            c.setProcessor(console.getProcessor());
            c.setMemory_amount(console.getMemory_amount());
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
