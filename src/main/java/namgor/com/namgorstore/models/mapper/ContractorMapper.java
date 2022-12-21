package namgor.com.namgorstore.models.mapper;

import lombok.AllArgsConstructor;
import namgor.com.namgorstore.models.entities.Contractor;
import namgor.com.namgorstore.models.dto.ContractorDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.EnableWebFlux;

@Component
@EnableWebFlux
@AllArgsConstructor
public class ContractorMapper {

    private final ModelMapper mapper;

    public ContractorDto fromContractorToContractorDto(Contractor Contractor){
        return mapper
                .map(Contractor, ContractorDto.class);
    }

    public Contractor fromContractorDtoToContractor(ContractorDto ContractorDto){
        return mapper
                .map(ContractorDto, Contractor.class);
    }
}