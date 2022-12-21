package namgor.com.namgorstore.services;

import namgor.com.namgorstore.dao.ProductDao;
import namgor.com.namgorstore.models.dto.ProductDto;
import namgor.com.namgorstore.models.entities.Contractor;
import namgor.com.namgorstore.models.entities.Product;
import namgor.com.namgorstore.models.mapper.ProductMapper;
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

@SpringBootTest
class ProductServiceTest {

    @MockBean
    private ProductService service;

    @Autowired
    private ProductMapper mapper;

    @Mock
    ProductDao dao;

    @BeforeEach
    void setUp() {
        service = new ProductService(dao, mapper);
    }

    @Test
    void shouldGetAllProducts() {
        var contractor1 = new Contractor();
        contractor1.setId("contractor1");
        contractor1.setName("Polymetal");
        contractor1.setPassport("AO804867");

        var product1 = new Product();
        product1.setId("product1");
        product1.setName("Core drilling");
        product1.setDescription("Test text");
        product1.setStock(100);
        product1.setPrice(200.0);
        product1.setProviderId(contractor1.getId());
        product1.setMinimumAmount(50);
        product1.setMaximumAmount(1000);

        Mockito
                .when(dao.findAll())
                .thenReturn(Flux.just(product1));
        Flux<ProductDto> productDtoFlux = service.getAllProducts();

        StepVerifier
                .create(productDtoFlux)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(dao).findAll();
    }

    @Test
    void shouldSaveProduct() {
        var contractor1 = new Contractor();
        contractor1.setId("contractor1");
        contractor1.setName("Polymetal");
        contractor1.setPassport("AO804867");

       var product1 = new ProductDto();
        product1.setId("product1");
        product1.setName("Core drilling");
        product1.setDescription("Test text");
        product1.setStock(100);
        product1.setPrice(200.0);
        product1.setProviderId(contractor1.getId());
        product1.setMinimumAmount(50);
        product1.setMaximumAmount(1000);

        StepVerifier
                .create(Mono.just(
                        Mockito.when(
                                service.postProduct(product1))
                .thenReturn(Mono.just(product1))))
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }
}