package namgor.com.namgorstore.models.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document
public class Invoice {
    @Id
    private String id;
    private LocalDate date;
    private String clientName;
    private String employeeName;
    private List<ProductSold> products;
    private Double totalPrice;
}
