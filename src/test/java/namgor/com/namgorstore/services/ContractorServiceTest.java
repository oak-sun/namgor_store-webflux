package namgor.com.namgorstore.services;

import namgor.com.namgorstore.dao.ContractorDao;
import namgor.com.namgorstore.models.dto.ContractorDto;
import namgor.com.namgorstore.models.entities.Contractor;
import namgor.com.namgorstore.models.mapper.ContractorMapper;
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
class ContractorServiceTest {

    @MockBean
    private ContractorService service;

    @Autowired
    private ContractorMapper mapper;

    @Mock
    ContractorDao dao;

    @BeforeEach
    void setUp() {
        service = new ContractorService(dao, mapper);
    }

    @Test
    void shouldGetAllContractors() {
        var contractor1 = new Contractor();
        contractor1.setId("contractor1");
        contractor1.setName("Polymetal");
        contractor1.setPassport("AO804867");

        var contractor2 = new Contractor();
        contractor2.setId("contractor2");
        contractor2.setName("N-MINING LIMITED");
        contractor2.setPassport("BW946873");

        Mockito
                .when(dao.findAll())
                .thenReturn(Flux.just(contractor1,
                                      contractor2));
        Flux<ContractorDto> contractorDtoFlux = service.getAllContractors();

        StepVerifier
                .create(contractorDtoFlux)
                .expectNextCount(2)
                .verifyComplete();

        Mockito
                .verify(dao)
                .findAll();
    }

    @Test
    void shouldSaveContractor() {
        var contractor1 = new ContractorDto();
        contractor1.setId("contractor1");
        contractor1.setName("Polymetal");
        contractor1.setPassport("AO804867");

        StepVerifier
                .create(Mono.just(
                        Mockito.when(service.postContractor(contractor1))
                .thenReturn(Mono.just(contractor1))))
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }
}