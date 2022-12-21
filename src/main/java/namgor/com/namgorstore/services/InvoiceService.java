package namgor.com.namgorstore.services;

import lombok.AllArgsConstructor;
import namgor.com.namgorstore.models.dto.InvoiceDto;
import namgor.com.namgorstore.models.mapper.InvoiceMapper;
import namgor.com.namgorstore.dao.InvoiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class InvoiceService {

    @Autowired
    private InvoiceDao dao;

    @Autowired
    private InvoiceMapper mapper;

    public Flux<InvoiceDto> getAllInvoices() {
        return dao
                .findAll()
                .map(mapper::fromInvoiceToInvoiceDto);
    }


    public Mono<InvoiceDto> postInvoice(InvoiceDto dto){
        dto.setDate(LocalDate.now());
        return dao
                .save(mapper.fromInvoiceDtoToInvoice(dto))
                .map(mapper::fromInvoiceToInvoiceDto);
    }
}
