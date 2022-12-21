package namgor.com.namgorstore.models.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String id;
    private String name;
    private String description;
    private Integer stock;
    private Double price;
    private String providerId;
    private Integer minimumAmount;
    private Integer maximumAmount;
}
