package com.company.capstoneproject.controller;

import com.company.capstoneproject.service.ServiceLayer;
import com.company.capstoneproject.viewmodel.InvoiceViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer service;

    private ObjectMapper mapper = new ObjectMapper();

    private InvoiceViewModel invoice;
    private String invoiceJson;
    private List<InvoiceViewModel> allInvoices = new ArrayList<>();
    private String allInvoicesJson;

    @Before
    public void setUp() throws Exception {
        invoice = new InvoiceViewModel();
        invoice.setId(1);
        invoice.setItem_id(269);
        invoice.setQuantity(12);
        invoice.setItem_type("Game");
        invoice.setName("Customer 1");
        invoice.setStreet("100 Main Street");
        invoice.setCity("Clovis");
        invoice.setState("CA");
        invoice.setZipcode("93612");
        invoice.setProcessing_fee(new BigDecimal("16.98"));
        invoice.setSubtotal(new BigDecimal("155.88"));
        invoice.setTax(new BigDecimal("9.35"));
        invoice.setTotal(new BigDecimal("182.21"));
        invoice.setUnit_price(new BigDecimal("12.99"));

        invoiceJson = mapper.writeValueAsString(invoice);

        allInvoices.add(invoice);

        allInvoicesJson = mapper.writeValueAsString(allInvoices);
    }

    // Testing GET all
    @Test
    public void shouldReturnAllInvoices() throws Exception {
        doReturn(allInvoices).when(service).findAllInvoices();

        mockMvc.perform(
                        get("/invoice"))
                .andExpect(status().isOk())
                .andExpect(content().json(allInvoicesJson)
                );
    }

    // Testing GET by id
    @Test
    public void shouldReturnInvoiceById() throws Exception {
        doReturn(invoice).when(service).findInvoice(1);

        ResultActions result = mockMvc.perform(
                        get("/invoice/{id}", 1))
                .andExpect(status().isOk())
                .andExpect((content().json(invoiceJson))
                );
    }

    // Testing GET by Name
    @Test
    public void shouldReturnInvoiceByName() throws Exception {
        doReturn(invoice).when(service).findInvoiceByName("Customer 1");

        ResultActions result = mockMvc.perform(
                        get("/invoice/name/{name}", "Customer 1"))
                .andExpect(status().isOk())
                .andExpect((content().json(invoiceJson))
                );
    }

    //Testing POST
    @Test
    public void shouldCreateTShirtOnPostRequest() throws Exception {
        //create new invoice
        InvoiceViewModel inputInvoice = new InvoiceViewModel();
        inputInvoice.setId(1);
        inputInvoice.setItem_id(269);
        inputInvoice.setQuantity(12);
        inputInvoice.setItem_type("Game");
        inputInvoice.setName("Customer 1");
        inputInvoice.setStreet("100 Main Street");
        inputInvoice.setCity("Clovis");
        inputInvoice.setState("CA");
        inputInvoice.setZipcode("93612");

        //change to Json input to test Post
        String inputJson = mapper.writeValueAsString(inputInvoice);

        //It should return shirt when repo saves inputInvoice
        doReturn(invoice).when(service).saveInvoice(inputInvoice);

        //perform post
        mockMvc.perform(
                        post("/invoice")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(invoiceJson));

    }
}
