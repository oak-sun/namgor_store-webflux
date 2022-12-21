package namgor.com.namgorstore.models.mapper;

import lombok.AllArgsConstructor;
import namgor.com.namgorstore.models.entities.PayCheck;
import namgor.com.namgorstore.models.dto.PayCheckDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.EnableWebFlux;

@Component
@EnableWebFlux
@AllArgsConstructor
public class PayCheckMapper {

    private final ModelMapper mapper;

    public PayCheckDto fromPayCheckToPayCheckDto(PayCheck check){
        return mapper.map(check, PayCheckDto.class);
    }

    public PayCheck fromPayCheckDtoToPayCheck(PayCheckDto dto){
        return mapper.map(dto, PayCheck.class);
    }
}