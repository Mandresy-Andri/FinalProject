package com.company.capstoneproject.controller;

import com.company.capstoneproject.service.ServiceLayer;
import com.company.capstoneproject.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class InvoiceController {

    @Autowired
    ServiceLayer serviceLayer;

    @GetMapping("/invoice")
    public List<InvoiceViewModel> getInvoices() {

        return serviceLayer.findAllInvoices();
    }

    @GetMapping("/invoice/{id}")
    public InvoiceViewModel getInvoiceById(@PathVariable int id) {

        return serviceLayer.findInvoice(id);
    }

    @GetMapping("/invoice/name/{name}")
    public InvoiceViewModel getInvoiceByName(@PathVariable String name) {

        return serviceLayer.findInvoiceByName(name);
    }

    @PostMapping("/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel addInvoice(@RequestBody @Valid InvoiceViewModel invoiceViewModel) {

        return serviceLayer.saveInvoice(invoiceViewModel);
    }
}
