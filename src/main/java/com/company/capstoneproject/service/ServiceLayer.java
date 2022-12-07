package com.company.capstoneproject.service;

import com.company.capstoneproject.model.*;
import com.company.capstoneproject.repository.*;
import com.company.capstoneproject.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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


        int id = a.getItem_id();
        String type = a.getItem_type();
        int quantity = a.getQuantity();
        System.out.println(id+", "+type+", "+quantity);

        //price of the item (method also check if object exists, quantity is valid, and updates new quantity)
        BigDecimal unit_price = GetUnitPrice(type,id,quantity);
        //basic fee + 15.49 if quantity>10
        BigDecimal processing_fee = GetProcessingFee(type, quantity);
        //price * quantity
        BigDecimal subtotal = unit_price.multiply(BigDecimal.valueOf(quantity));
        // percentage of subtotal based on state (method also checks if state is valid
        BigDecimal tax = GetAndCheckTax(viewModel.getState(),subtotal);
        // sum of everything
        BigDecimal total = processing_fee.add(subtotal).add(tax).setScale(2, RoundingMode.HALF_UP);

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
        System.out.println(id+" "+type+", "+quantity);
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
                throw new IllegalArgumentException("wrong item type");
        }

        return answer;
    }

    //checks if item type is valid then returns processing fee
    public BigDecimal GetProcessingFee(String type, int quantity) {
        BigDecimal answer = null;
        //Optional<ProcessingFee> processingFee = processingFeeRepository.findById(type);
        Map<String, BigDecimal> fees = new HashMap<>();
        fees.put("Console",new BigDecimal(14.99));
        fees.put("Game",new BigDecimal(1.49));
        fees.put("T-Shirt",new BigDecimal(1.98));

        answer = fees.get(type);
//        if(processingFee.isPresent())
//            answer = processingFee.get().getFee();
//        else
//            throw new IllegalArgumentException("Wrong processing fee");

        if(quantity>10)
            answer = answer.add(new BigDecimal("15.49")).setScale(2, RoundingMode.HALF_UP);

        System.out.println(answer);
        return answer;
    }

    //Checks if state is valid then returns tax
    public BigDecimal GetAndCheckTax(String state,BigDecimal subtotal) {
        BigDecimal answer = null;
        //Optional<Tax> tax = taxRepository.findById(state);

        Map<String, BigDecimal> taxes = new HashMap<>();
        taxes.put("AL",new BigDecimal(0.05));
        taxes.put("AK",new BigDecimal(0.06));
        taxes.put("AZ",new BigDecimal(0.04));
        taxes.put("AR",new BigDecimal(0.06));
        taxes.put("CA",new BigDecimal(0.06));
        taxes.put("CO",new BigDecimal(0.04));
        taxes.put("CT",new BigDecimal(0.03));
        taxes.put("DE",new BigDecimal(0.05));
        taxes.put("FL",new BigDecimal(0.06));
        taxes.put("GA",new BigDecimal(0.07));
        taxes.put("HI",new BigDecimal(0.05));
        taxes.put("ID",new BigDecimal(0.03));
        taxes.put("IL",new BigDecimal(0.05));
        taxes.put("IN",new BigDecimal(0.05));
        taxes.put("IA",new BigDecimal(0.04));
        taxes.put("KS",new BigDecimal(0.06));
        taxes.put("KY",new BigDecimal(0.04));
        taxes.put("LA",new BigDecimal(0.05));
        taxes.put("ME",new BigDecimal(0.03));
        taxes.put("MD",new BigDecimal(0.07));
        taxes.put("MA",new BigDecimal(0.05));
        taxes.put("MI",new BigDecimal(0.06));
        taxes.put("MN",new BigDecimal(0.06));
        taxes.put("MO",new BigDecimal( .05));
        taxes.put("MT",new BigDecimal( .03));
        taxes.put ("NE",new BigDecimal( .04));
        taxes.put("NV",new BigDecimal( .04));
        taxes.put("NH",new BigDecimal( .06));
        taxes.put("NJ",new BigDecimal( .05));
        taxes.put("NM",new BigDecimal( .05));
        taxes.put("NY",new BigDecimal( .06));
        taxes.put("NC",new BigDecimal( .05));
        taxes.put("ND",new BigDecimal( .05));
        taxes.put("OH",new BigDecimal( .04));
        taxes.put("OK",new BigDecimal( .04));
        taxes.put("OR",new BigDecimal( .07));
        taxes.put("PA",new BigDecimal( .06));
        taxes.put("RI",new BigDecimal( .06));
        taxes.put ("SC",new BigDecimal( .06));
        taxes.put ("SD",new BigDecimal( .06));
        taxes.put ("TN",new BigDecimal( .05));
        taxes.put ("TX",new BigDecimal( .03));
        taxes.put("UT",new BigDecimal( .04));
        taxes.put("VT", new BigDecimal(.07));
        taxes.put("VA",new BigDecimal( .06));
        taxes.put("WA",new BigDecimal( .05));
        taxes.put ("WV",new BigDecimal( .05));
        taxes.put ("WI", new BigDecimal(.03));
        taxes.put ("WY",new BigDecimal (.04));

//        if(tax.isPresent())
//            answer = tax.get().getRate();
//        else
//            throw new IllegalArgumentException("Invalid State");
        answer = taxes.get(state);
        answer = answer.multiply(subtotal).setScale(2, RoundingMode.HALF_UP);

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
