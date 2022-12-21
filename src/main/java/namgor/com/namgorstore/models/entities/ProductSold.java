package namgor.com.namgorstore.models.entities;

import lombok.Data;

@Data
public class ProductSold {
    private String id;
    private String name;
    private Double price;
    private Integer amount;
}
