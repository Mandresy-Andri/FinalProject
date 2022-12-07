package com.company.capstoneproject.repository;

import com.company.capstoneproject.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<Tax,String> {
}
