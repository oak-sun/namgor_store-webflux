package namgor.com.namgorstore.services;

import namgor.com.namgorstore.dao.PayCheckDao;
import namgor.com.namgorstore.models.dto.PayCheckDto;
import namgor.com.namgorstore.models.dto.ProductDto;
import namgor.com.namgorstore.models.entities.Contractor;
import namgor.com.namgorstore.models.entities.PayCheck;
import namgor.com.namgorstore.models.entities.Product;
import namgor.com.namgorstore.models.mapper.PayCheckMapper;
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

@SpringBootTest
class PayCheckServiceTest {

    @MockBean
    private PayCheckService service;

    @Autowired
    private PayCheckMapper mapper;

    @Mock
    PayCheckDao dao;

    @BeforeEach
    void setUp() {
        service = new PayCheckService(dao, mapper);
    }

    @Test
    void shouldGetAllPayChecks() {
        var contractor1 = new Contractor();
        contractor1.setId("contractor1");
        var product1 = new Product();
        product1.setId("product1");

        var payCheck1 = new PayCheck();
        payCheck1.setId("payCheck1");
        payCheck1.setDate(LocalDate.now());
        payCheck1.setProductId(product1.getId());
        payCheck1.setProductsAmount(5);
        payCheck1.setProviderId(contractor1.getId());

        Mockito
                .when(dao.findAll())
                .thenReturn(Flux.just(payCheck1));
        Flux<PayCheckDto> paycheckDtoFlux = service.getAllPayChecks();

        StepVerifier
                .create(paycheckDtoFlux)
                .expectNextCount(1)
                .verifyComplete();

        Mockito
                .verify(dao)
                .findAll();
    }

    @Test
    void shouldSavePayCheck() {
        var contractor1 = new Contractor();
        contractor1.setId("contractor1");

        var product1 = new ProductDto();
        product1.setId("product1");

        var paycheck1 = new PayCheckDto();
        paycheck1.setId("paycheck1");
        paycheck1.setDate(LocalDate.now());
        paycheck1.setProductId(product1.getId());
        paycheck1.setProductsAmount(5);
        paycheck1.setProviderId(contractor1.getId());

        StepVerifier
                .create(Mono.just(
                        Mockito.when(service.postPayCheck(paycheck1))
                .thenReturn(Mono.just(paycheck1))))
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }
}