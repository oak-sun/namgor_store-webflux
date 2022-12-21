package namgor.com.namgorstore.services;

import lombok.AllArgsConstructor;
import namgor.com.namgorstore.models.dto.ContractorDto;
import namgor.com.namgorstore.models.mapper.ContractorMapper;
import namgor.com.namgorstore.dao.ContractorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ContractorService {

    @Autowired
    private ContractorDao dao;

    @Autowired
    private ContractorMapper mapper;

    public Flux<ContractorDto> getAllContractors() {
        return dao
                .findAll()
                .map(mapper::fromContractorToContractorDto);
    }

    public Mono<ContractorDto> postContractor(ContractorDto dto){
        return dao
                .save(mapper
                        .fromContractorDtoToContractor(dto))
                .map(mapper::fromContractorToContractorDto);
    }
}
