package com.company.capstoneproject.repository;

import com.company.capstoneproject.model.Console;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsoleRepository extends JpaRepository<Console, Integer>{
    List<Console> getConsoleByManufacturer(String manufacturer);

}


