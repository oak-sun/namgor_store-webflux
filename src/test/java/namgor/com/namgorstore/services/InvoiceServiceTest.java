package namgor.com.namgorstore.services;

import namgor.com.namgorstore.dao.InvoiceDao;
import namgor.com.namgorstore.models.dto.InvoiceDto;
import namgor.com.namgorstore.models.entities.Invoice;
import namgor.com.namgorstore.models.entities.ProductSold;
import namgor.com.namgorstore.models.mapper.InvoiceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class InvoiceServiceTest {

    @MockBean
    private InvoiceService service;

    @Autowired
    private InvoiceMapper mapper;

    @Mock
    InvoiceDao dao;

    @BeforeEach
    void setUp() {
        service = new InvoiceService(dao, mapper);
    }

    @Test
    void shouldGetAllInvoices() {
        var productSold1 = new ProductSold();
        productSold1.setId("product1");
        productSold1.setName("Core drilling");
        productSold1.setPrice(200.0);
        productSold1.setAmount(10);

        var productSold2 = new ProductSold();
        productSold2.setId("product2");
        productSold2.setName("Mining workings");
        productSold2.setPrice(12500.0);
        productSold2.setAmount(1);

        var invoice1 = new Invoice();
        invoice1.setId("invoice1");
        invoice1.setDate(LocalDate.now());
        invoice1.setClientName("Vladimir");
        invoice1.setEmployeeName("Maria");

        List<ProductSold> products = new ArrayList<>();
        products.add(productSold1);
        products.add(productSold2);
        invoice1.setProducts(products);
        invoice1.setTotalPrice(14500.0);

        Mockito
                .when(dao.findAll())
                .thenReturn(Flux.just(invoice1));
        Flux<InvoiceDto> invoiceDtoFlux = service.getAllInvoices();

        StepVerifier
                .create(invoiceDtoFlux)
                .expectNextCount(1)
                .verifyComplete();
        Mockito.verify(dao).findAll();
    }

    @Test
    void shouldSaveInvoice() {
        var productSold1 = new ProductSold();
        productSold1.setId("product1");
        productSold1.setName("Core drilling");
        productSold1.setPrice(200.0);
        productSold1.setAmount(10);

        var productSold2 = new ProductSold();
        productSold2.setId("product2");
        productSold2.setName("Mining workings");
        productSold2.setPrice(12500.0);
        productSold2.setAmount(1);

        var invoice1 = new InvoiceDto();
        invoice1.setId("invoice1");
        invoice1.setDate(LocalDate.now());
        invoice1.setClientName("Vladimir");
        invoice1.setEmployeeName("Maria");

        List<ProductSold> products = new ArrayList<>();
        products.add(productSold1);
        products.add(productSold2);
        invoice1.setProducts(products);
        invoice1.setTotalPrice(14500.0);

        StepVerifier
                .create(Mono.just(
                        Mockito
                                .when(service.postInvoice(invoice1))
                .thenReturn(Mono.just(invoice1))))
                .expectNextCount(1).expectComplete().verify();
    }
}

