package namgor.com.namgorstore.models.mapper;

import lombok.AllArgsConstructor;
import namgor.com.namgorstore.models.entities.Invoice;
import namgor.com.namgorstore.models.dto.InvoiceDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.EnableWebFlux;

@Component
@EnableWebFlux
@AllArgsConstructor
public class InvoiceMapper {

    private final ModelMapper mapper;

    public InvoiceDto fromInvoiceToInvoiceDto(Invoice invoice){
        return mapper.map(invoice, InvoiceDto.class);
    }

    public Invoice fromInvoiceDtoToInvoice(InvoiceDto dto){
        return mapper.map(dto, Invoice.class);
    }
}
