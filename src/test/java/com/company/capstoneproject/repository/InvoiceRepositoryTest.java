package com.company.capstoneproject.repository;

import com.company.capstoneproject.model.Invoice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceRepositoryTest {

    @Autowired
    InvoiceRepository repo;

    @Before
    public void setUp() throws Exception {
        repo.deleteAll();
    }

    @Test
    public void addGetDeleteInvoice() {

        Invoice invoice = new Invoice();
        invoice.setItem_id(5);
        invoice.setQuantity(7);
        invoice.setItem_type("T-Shirt");
        invoice.setName("Michel");
        invoice.setStreet("Long");
        invoice.setCity("Ville");
        invoice.setState("CA");
        invoice.setZipcode("22312");
        //fake numbers. Logic will be tested in service layer test
        invoice.setProcessing_fee(new BigDecimal("15.00"));
        invoice.setSubtotal(new BigDecimal("35.00"));
        invoice.setTax(new BigDecimal("5.00"));
        invoice.setTotal(new BigDecimal("55.00"));
        invoice.setUnit_price(new BigDecimal("5.00"));

        repo.save(invoice);

        //get invoice by id
        Optional<Invoice> invoiceAnswer = repo.findById(invoice.getId());

        //assert that we get correct invoice
        assertEquals(invoiceAnswer.get(), invoice);

        //delete tshirt
        repo.deleteById(invoice.getId());

        //assert that tshirt was deleted
        invoiceAnswer = repo.findById(invoice.getId());
        assertFalse(invoiceAnswer.isPresent());
    }

    @Test
    public void getAllInvoices() {
        //create invoice 1
        Invoice invoice = new Invoice();
        invoice.setItem_id(5);
        invoice.setQuantity(7);
        invoice.setItem_type("T-Shirt");
        invoice.setName("Michel");
        invoice.setStreet("Long");
        invoice.setCity("Ville");
        invoice.setState("CA");
        invoice.setZipcode("22312");
        //fake numbers. Logic will be tested in service layer test
        invoice.setProcessing_fee(new BigDecimal("15.00"));
        invoice.setSubtotal(new BigDecimal("35.00"));
        invoice.setTax(new BigDecimal("5.00"));
        invoice.setTotal(new BigDecimal("55.00"));
        invoice.setUnit_price(new BigDecimal("5.00"));
        repo.save(invoice);

        //create invoice 2
        Invoice invoice2 = new Invoice();
        invoice2.setItem_id(3);
        invoice2.setQuantity(7);
        invoice2.setItem_type("Console");
        invoice2.setName("William");
        invoice2.setStreet("Short");
        invoice2.setCity("Alexandria");
        invoice2.setState("VA");
        invoice2.setZipcode("46852");
        //fake numbers. Logic will be tested in service layer test
        invoice2.setProcessing_fee(new BigDecimal("15.00"));
        invoice2.setSubtotal(new BigDecimal("35.00"));
        invoice2.setTax(new BigDecimal("5.00"));
        invoice2.setTotal(new BigDecimal("55.00"));
        invoice2.setUnit_price(new BigDecimal("5.00"));
        repo.save(invoice2);

        //get all tshirts
        List<Invoice> invoiceList = repo.findAll();

        //assert all tshirts were retrieved
        assertEquals(invoiceList.size(), 2);

    }

    @Test
    public void getInvoiceByName() {
        //create invoice 1
        Invoice invoice = new Invoice();
        invoice.setItem_id(5);
        invoice.setQuantity(7);
        invoice.setItem_type("T-Shirt");
        invoice.setName("Michel");
        invoice.setStreet("Long");
        invoice.setCity("Ville");
        invoice.setState("CA");
        invoice.setZipcode("22312");
        //fake numbers. Logic will be tested in service layer test
        invoice.setProcessing_fee(new BigDecimal("15.00"));
        invoice.setSubtotal(new BigDecimal("35.00"));
        invoice.setTax(new BigDecimal("5.00"));
        invoice.setTotal(new BigDecimal("55.00"));
        invoice.setUnit_price(new BigDecimal("5.00"));
        repo.save(invoice);

        //get all tshirts
        Optional<Invoice> invoiceAnswer = repo.findByName("Michel");

        //assert all tshirts were retrieved
        assertEquals(invoiceAnswer.get(), invoice);
    }
}

