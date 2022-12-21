package namgor.com.namgorstore.models.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PayCheckDto {
    private String id;
    private LocalDate date;
    private String productId;
    private Integer productsAmount;
    private String providerId;
}
