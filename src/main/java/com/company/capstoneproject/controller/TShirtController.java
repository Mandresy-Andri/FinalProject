package com.company.capstoneproject.controller;

import com.company.capstoneproject.model.TShirt;
import com.company.capstoneproject.repository.TShirtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class TShirtController {
    @Autowired
    TShirtRepository repo;

    //get all t-shirts
    @GetMapping("/tshirt")
    public List<TShirt> getTShirt() {
        return repo.findAll();
    }

    //get t-shirt by size
    @GetMapping("/tshirt/size/{size}")
    public List<TShirt> getTShirtBySize(@PathVariable String size) {
        Optional<List<TShirt>> returnVal = repo.findBySize(size);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    //get t-shirt by color
    @GetMapping("/tshirt/color/{color}")
    public List<TShirt> getTShirtByColor(@PathVariable String color) {
        Optional<List<TShirt>> returnVal = repo.findByColor(color);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    //get t-shirt by id
    @GetMapping("/tshirt/{id}")
    public TShirt getTShirtById(@PathVariable int id) {
        Optional<TShirt> returnVal = repo.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    //add t-shirt
    @PostMapping("/tshirt")
    @ResponseStatus(HttpStatus.CREATED)
    public TShirt addTShirt(@RequestBody @Valid TShirt tShirt) {
        return repo.save(tShirt);
    }

    //update t-shirt
    @PutMapping("/tshirt")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTShirt(@RequestBody TShirt tShirt) {
        repo.save(tShirt);
    }

    //delete t-shirt
    @DeleteMapping("/tshirt/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTShirt(@PathVariable int id) {
        repo.deleteById(id);
    }
}
