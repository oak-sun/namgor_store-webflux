package namgor.com.namgorstore.services;

import lombok.AllArgsConstructor;
import namgor.com.namgorstore.models.dto.PayCheckDto;
import namgor.com.namgorstore.models.mapper.PayCheckMapper;
import namgor.com.namgorstore.dao.PayCheckDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class PayCheckService {

    @Autowired
    private PayCheckDao dao;

    @Autowired
    private PayCheckMapper mapper;


    public Flux<PayCheckDto> getAllPayChecks() {
        return dao
                .findAll()
                .map(mapper::fromPayCheckToPayCheckDto);
    }

    public Mono<PayCheckDto> postPayCheck(PayCheckDto dto) {
        dto.setDate(LocalDate.now());
        return dao
                .save(mapper.fromPayCheckDtoToPayCheck(dto))
                .map(mapper::fromPayCheckToPayCheckDto);
    }
}
