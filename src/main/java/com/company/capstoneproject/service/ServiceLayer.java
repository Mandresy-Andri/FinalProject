package com.company.capstoneproject.service;

import com.company.capstoneproject.repository.InvoiceRepository;
import com.company.capstoneproject.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//EMPTY METHODS THAT WE STILL NEED TO COMPLETE
@Component
public class ServiceLayer {

    @Autowired
    InvoiceRepository repo;

    @Transactional
    public InvoiceViewModel saveInvoice(InvoiceViewModel viewModel) { return viewModel;}

    @Transactional
    public InvoiceViewModel findInvoiceByName(String name) { return new InvoiceViewModel();}

    @Transactional
    public InvoiceViewModel findInvoice(int id) {return new InvoiceViewModel();}

    @Transactional
    public List<InvoiceViewModel> findAllInvoices() { return new ArrayList<InvoiceViewModel>();}
}
