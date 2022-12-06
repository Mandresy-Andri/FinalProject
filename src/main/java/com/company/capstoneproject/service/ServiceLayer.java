package com.company.capstoneproject.service;

import com.company.capstoneproject.model.*;
import com.company.capstoneproject.repository.*;
import com.company.capstoneproject.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ServiceLayer {

    private InvoiceRepository invoiceRepository;
    private ConsoleRepository consoleRepository;
    private GameRepository gameRepository;
    private TShirtRepository tShirtRepository;
    private TaxRepository taxRepository;
    private ProcessingFeeRepository processingFeeRepository;

    @Autowired
    public ServiceLayer(InvoiceRepository invoiceRepository, ConsoleRepository consoleRepository,
                        GameRepository gameRepository, TShirtRepository tShirtRepository,
                        ProcessingFeeRepository processingFeeRepository,TaxRepository taxRepository) {
        this.invoiceRepository = invoiceRepository;
        this.consoleRepository = consoleRepository;
        this.gameRepository = gameRepository;
        this.tShirtRepository = tShirtRepository;
        this.taxRepository = taxRepository;
        this.processingFeeRepository = processingFeeRepository;
    }



    @Transactional
    public InvoiceViewModel saveInvoice(InvoiceViewModel viewModel) {
        // Persist Invoice
        Invoice a = new Invoice();
        a.setItem_id(viewModel.getItem_id());
        a.setItem_type(viewModel.getItem_type());
        a.setQuantity(viewModel.getQuantity());
        a.setName(viewModel.getName());
        a.setStreet(viewModel.getStreet());
        a.setCity(viewModel.getCity());
        a.setState(viewModel.getState());
        a.setZipcode(viewModel.getZipcode());
        a = invoiceRepository.save(a);
        viewModel.setId(a.getId());


        int id = viewModel.getItem_id();
        String type = viewModel.getItem_type();
        int quantity = viewModel.getQuantity();

        //price of the item (method also check if object exists, quantity is valid, and updates new quantity)
        BigDecimal unit_price = GetUnitPrice(type,id,quantity);
        //basic fee + 15.49 if quantity>10
        BigDecimal processing_fee = GetProcessingFee(type, quantity);
        //price * quantity
        BigDecimal subtotal = unit_price.multiply(BigDecimal.valueOf(quantity));
        // percentage of subtotal based on state (method also checks if state is valid
        BigDecimal tax = GetAndCheckTax(viewModel.getState(),subtotal);
        // sum of everything
        BigDecimal total = processing_fee.add(subtotal).add(tax);

        a.setProcessing_fee(processing_fee);
        a.setSubtotal(subtotal);
        a.setTax(tax);
        a.setUnit_price(unit_price);
        a.setTotal(total);

        return buildInvoiceViewModel(a);
    }

    //checks if object of item type and id exists
    //checks if quantity is enough
    //updates Object quantity
    //returns the unit price of the verified object
    public BigDecimal GetUnitPrice(String type,int id,int quantity) {
        BigDecimal answer = null;
        switch (type){
            case "Console":
                Optional<Console> console = consoleRepository.findById(id);
                //check if console exists
                if(console.isPresent())
                    answer = console.get().getPrice();
                else
                    throw new IllegalArgumentException("No Console with id: "+id);
                //check if quantity is valid
                if(console.get().getQuantity()<quantity)
                    throw new IllegalArgumentException("Quantity asked is over available number");
                else
                    console.get().setQuantity(console.get().getQuantity()-quantity);
                break;

            case "T-Shirt":
                Optional<TShirt> tShirt = tShirtRepository.findById(id);
                //check if t shirt exists
                if(tShirt.isPresent())
                    answer = tShirt.get().getPrice();
                else
                    throw new IllegalArgumentException("No T-Shirt with id: "+id);
                //check if quantity is valid
                if(tShirt.get().getQuantity()<quantity)
                    throw new IllegalArgumentException("Quantity asked is over available number");
                else
                    tShirt.get().setQuantity(tShirt.get().getQuantity()-quantity);
                break;

            case "Game":
                Optional<Game> game = gameRepository.findById(id);
                //check if game exists
                if(game.isPresent())
                    answer = game.get().getPrice();
                else
                    throw new IllegalArgumentException("No Game with id: "+id);
                //check if quantity is valid
                if(game.get().getQuantity()<quantity)
                    throw new IllegalArgumentException("Quantity asked is over available number");
                else
                    game.get().setQuantity(game.get().getQuantity()-quantity);
                break;
            default:
                throw new IllegalArgumentException("Invalid item type");
        }

        return answer;
    }

    //checks if item type is valid then returns processing fee
    public BigDecimal GetProcessingFee(String type, int quantity) {
        BigDecimal answer = null;
        Optional<ProcessingFee> processingFee = processingFeeRepository.findById(type);
        if(processingFee.isPresent())
            answer = processingFee.get().getFee();
        else
            throw new IllegalArgumentException("Invalid item type");
        if(quantity>10)
            answer = answer.add(new BigDecimal("15.49"));

        return answer;
    }

    //Checks if state is valid then returns tax
    public BigDecimal GetAndCheckTax(String state,BigDecimal subtotal) {
        BigDecimal answer = null;
        Optional<Tax> tax = taxRepository.findById(state);
        if(tax.isPresent())
            answer = tax.get().getRate();
        else
            throw new IllegalArgumentException("Invalid State");

        answer = answer.multiply(subtotal).setScale(2, RoundingMode.HALF_UP);;

        return answer;
    }

    @Transactional
    public InvoiceViewModel findInvoiceByName(String name) {
        // Get the invoice object first
        Optional<Invoice> invoice = invoiceRepository.findByName(name);

        return invoice.isPresent() ? buildInvoiceViewModel(invoice.get()) : null;
    }

    @Transactional
    public InvoiceViewModel findInvoice(int id) {
        // Get the album object first
        Optional<Invoice> invoice = invoiceRepository.findById(id);

        return invoice.isPresent() ? buildInvoiceViewModel(invoice.get()) : null;
    }

    @Transactional
    public List<InvoiceViewModel> findAllInvoices() {
        List<Invoice> invoiceList = invoiceRepository.findAll();

        List<InvoiceViewModel> ivmList = new ArrayList<>();

        for (Invoice invoice : invoiceList) {
            InvoiceViewModel avm = buildInvoiceViewModel(invoice);
            ivmList.add(avm);
        }

        return ivmList;
    }

    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {
        //set up the view model
        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setId(invoice.getId());
        ivm.setItem_id(invoice.getItem_id());
        ivm.setItem_type(invoice.getItem_type());
        ivm.setQuantity(invoice.getQuantity());
        ivm.setName(invoice.getName());
        ivm.setStreet(invoice.getStreet());
        ivm.setCity(invoice.getCity());
        ivm.setState(invoice.getState());
        ivm.setZipcode(invoice.getZipcode());
        ivm.setProcessing_fee(invoice.getProcessing_fee());
        ivm.setSubtotal(invoice.getSubtotal());
        ivm.setTax(invoice.getTax());
        ivm.setTotal(invoice.getTotal());
        ivm.setUnit_price(invoice.getUnit_price());

        // Return the InvoiceViewModel
        return ivm;
    }
}
