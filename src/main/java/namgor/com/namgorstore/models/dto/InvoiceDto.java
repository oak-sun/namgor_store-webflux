package namgor.com.namgorstore.models.dto;

import namgor.com.namgorstore.models.entities.ProductSold;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class InvoiceDto {
    private String id;
    private LocalDate date;
    private String clientName;
    private String employeeName;
    private List<ProductSold> products;
    private Double totalPrice;
}
