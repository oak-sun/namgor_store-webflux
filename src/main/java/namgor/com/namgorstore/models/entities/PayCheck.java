package namgor.com.namgorstore.models.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document
public class PayCheck {
    @Id
    private String id;
    private LocalDate date;
    private String productId;
    private Integer productsAmount;
    private String providerId;
}
