package com.company.capstoneproject.service;

import com.company.capstoneproject.model.*;
import com.company.capstoneproject.repository.*;
import com.company.capstoneproject.viewmodel.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class ServiceLayerTest {

    ServiceLayer service;

    InvoiceRepository invoiceRepository;
    ConsoleRepository consoleRepository;
    GameRepository gameRepository;
    TShirtRepository tShirtRepository;
    ProcessingFeeRepository processingFeeRepository;
    TaxRepository taxRepository;

    @Before
    public void SetUp() throws Exception {
        setUpConsoleRepoMock();
        setUpGameRepoMock();
        setUpTShirtRepoMock();
        setUpInvoiceRepoMock();
        setUpProcessingFeeRepoMock();
        setUpTaxRepoMock();


        service = new ServiceLayer(invoiceRepository,consoleRepository,gameRepository,tShirtRepository, processingFeeRepository, taxRepository);
    }

    private void setUpProcessingFeeRepoMock() {
        processingFeeRepository = mock(ProcessingFeeRepository.class);
        ProcessingFee gameFee = new ProcessingFee();
        gameFee.setFee(new BigDecimal("1.49"));
        gameFee.setProductType("Game");

        ProcessingFee shirtFee = new ProcessingFee();
        shirtFee.setFee(new BigDecimal("1.98"));
        shirtFee.setProductType("T-Shirt");

        ProcessingFee consoleFee = new ProcessingFee();
        consoleFee.setFee(new BigDecimal("14.99"));
        consoleFee.setProductType("Console");

        doReturn(Optional.of(gameFee)).when(processingFeeRepository).findById("Game");
        doReturn(Optional.of(shirtFee)).when(processingFeeRepository).findById("T-Shirt");
        doReturn(Optional.of(consoleFee)).when(processingFeeRepository).findById("Console");
    }

    private void setUpTaxRepoMock() {
        taxRepository = mock(TaxRepository.class);
        Tax taxCA = new Tax();
        taxCA.setState("CA");
        taxCA.setRate(new BigDecimal(".06"));

        doReturn(Optional.of(taxCA)).when(taxRepository).findById("CA");
    }
    private void setUpTShirtRepoMock() {
        tShirtRepository = mock(TShirtRepository.class);
        TShirt shirt = new TShirt();
        shirt.setId(1);
        shirt.setSize("Large");
        shirt.setColor("Red");
        shirt.setDescription("Nice red shirt");
        shirt.setPrice(new BigDecimal("450.00"));
        shirt.setQuantity(5);

        TShirt shirt2 = new TShirt();
        shirt2.setSize("Large");
        shirt2.setColor("Red");
        shirt2.setDescription("Nice red shirt");
        shirt2.setPrice(new BigDecimal("450.00"));
        shirt2.setQuantity(5);

        List<TShirt> tList = new ArrayList<>();
        tList.add(shirt);

        doReturn(shirt).when(tShirtRepository).save(shirt2);
        doReturn(Optional.of(shirt)).when(tShirtRepository).findById(1);
        doReturn(tList).when(tShirtRepository).findAll();
    }

    private void setUpConsoleRepoMock() {
        consoleRepository = mock(ConsoleRepository.class);
        Console console = new Console();
        console.setConsole_id(1);
        console.setQuantity(5);
        console.setManufacturer("Pony");
        console.setPrice(new BigDecimal("325.00"));

        Console console2 = new Console();
        console2.setQuantity(5);
        console2.setManufacturer("Pony");
        console2.setPrice(new BigDecimal("325.00"));

        List<Console> cList = new ArrayList<>();
        cList.add(console);

        doReturn(console).when(consoleRepository).save(console2);
        doReturn(Optional.of(console)).when(consoleRepository).findById(1);
        doReturn(cList).when(consoleRepository).findAll();
    }

    private void setUpGameRepoMock() {
        gameRepository = mock(GameRepository.class);
        Game game = new Game();
        game.setGame_id(269);
        game.setTitle("Bario");
        game.setEsrbRating("10");
        game.setDescription("Jump on Tortoise");
        game.setPrice(new BigDecimal("12.99"));
        game.setStudio("NintenRe");
        game.setQuantity(14);

        Game game2 = new Game();
        game2.setTitle("Bario");
        game2.setEsrbRating("10");
        game2.setDescription("Jump on Tortoise");
        game2.setPrice(new BigDecimal("12.99"));
        game2.setStudio("NintenRe");
        game2.setQuantity(14);

        List<Game> gList = new ArrayList<>();
        gList.add(game);

        doReturn(game).when(gameRepository).save(game2);
        doReturn(Optional.of(game)).when(gameRepository).findById(269);
        doReturn(gList).when(gameRepository).findAll();
    }

    private void setUpInvoiceRepoMock() {
        invoiceRepository = mock(InvoiceRepository.class);
        Invoice invoice = new Invoice();
        invoice.setId(1);
        invoice.setItem_id(269);
        invoice.setQuantity(12);
        invoice.setItem_type("Game");
        invoice.setName("Customer 1");
        invoice.setStreet("100 Main Street");
        invoice.setCity("Clovis");
        invoice.setState("CA");
        invoice.setZipcode("93612");

        Invoice invoice2 = new Invoice();
        invoice2.setItem_id(269);
        invoice2.setQuantity(12);
        invoice2.setItem_type("Game");
        invoice2.setName("Customer 1");
        invoice2.setStreet("100 Main Street");
        invoice2.setCity("Clovis");
        invoice2.setState("CA");
        invoice2.setZipcode("93612");
        invoice2.setProcessing_fee(new BigDecimal(0));
        invoice2.setSubtotal(new BigDecimal(0));
        invoice2.setTax(new BigDecimal(0));
        invoice2.setTotal(new BigDecimal(0));
        invoice2.setUnit_price(new BigDecimal(0));

        List<Invoice> iList = new ArrayList<>();
        iList.add(invoice);

        doReturn(invoice).when(invoiceRepository).save(invoice2);
        doReturn(Optional.of(invoice)).when(invoiceRepository).findById(1);
        doReturn(Optional.of(invoice)).when(invoiceRepository).findByName("Customer 1");
        doReturn(iList).when(invoiceRepository).findAll();
    }

    @Test
    public void shouldCalculateAndSaveInvoice() {
        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setItem_id(269);
        invoice.setQuantity(12);
        invoice.setItem_type("Game");
        invoice.setName("Customer 1");
        invoice.setStreet("100 Main Street");
        invoice.setCity("Clovis");
        invoice.setState("CA");
        invoice.setZipcode("93612");

        InvoiceViewModel expected = new InvoiceViewModel();
        expected.setId(1);
        expected.setItem_id(269);
        expected.setQuantity(12);
        expected.setItem_type("Game");
        expected.setName("Customer 1");
        expected.setStreet("100 Main Street");
        expected.setCity("Clovis");
        expected.setState("CA");
        expected.setZipcode("93612");
        expected.setProcessing_fee(new BigDecimal("16.98"));
        expected.setSubtotal(new BigDecimal("155.88"));
        expected.setTax(new BigDecimal("9.35"));
        expected.setTotal(new BigDecimal("182.21"));
        expected.setUnit_price(new BigDecimal("12.99"));

        InvoiceViewModel invoiceViewModel = service.saveInvoice(invoice);
        assertEquals(expected,invoiceViewModel);
    }

    @Test
    public void shouldFindInvoiceByName() {
        InvoiceViewModel toCompare = new InvoiceViewModel();
        toCompare.setId(1);
        toCompare.setItem_id(269);
        toCompare.setQuantity(12);
        toCompare.setItem_type("Game");
        toCompare.setName("Customer 1");
        toCompare.setStreet("100 Main Street");
        toCompare.setCity("Clovis");
        toCompare.setState("CA");
        toCompare.setZipcode("93612");

        InvoiceViewModel invoiceViewModel = service.findInvoiceByName("Customer 1");
        assertEquals(toCompare,invoiceViewModel);
    }

    @Test
    public void shouldFindInvoiceById() {
        InvoiceViewModel toCompare = new InvoiceViewModel();
        toCompare.setId(1);
        toCompare.setItem_id(269);
        toCompare.setQuantity(12);
        toCompare.setItem_type("Game");
        toCompare.setName("Customer 1");
        toCompare.setStreet("100 Main Street");
        toCompare.setCity("Clovis");
        toCompare.setState("CA");
        toCompare.setZipcode("93612");

        InvoiceViewModel invoiceViewModel = service.findInvoice(1);
        assertEquals(toCompare,invoiceViewModel);
    }

    @Test
    public void shouldFindAllInvoices() {
        List<InvoiceViewModel> expected = new ArrayList<>();
        InvoiceViewModel toCompare = new InvoiceViewModel();
        toCompare.setId(1);
        toCompare.setItem_id(269);
        toCompare.setQuantity(12);
        toCompare.setItem_type("Game");
        toCompare.setName("Customer 1");
        toCompare.setStreet("100 Main Street");
        toCompare.setCity("Clovis");
        toCompare.setState("CA");
        toCompare.setZipcode("93612");
        expected.add(toCompare);

        List<InvoiceViewModel> invoiceViewModelList = service.findAllInvoices();
        assertEquals(expected, invoiceViewModelList);
    }
}
