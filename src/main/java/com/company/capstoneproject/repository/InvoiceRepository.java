package com.company.capstoneproject.repository;

import com.company.capstoneproject.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Integer> {

    Optional<Invoice> findByName(String name);
}
