package namgor.com.namgorstore.models.mapper;

import namgor.com.namgorstore.models.entities.Product;
import namgor.com.namgorstore.models.dto.ProductDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.EnableWebFlux;

@Component
@EnableWebFlux
public class ProductMapper {

    private final ModelMapper mapper;

    public ProductMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ProductDto fromProductToProductDto(Product Product){
        return mapper.map(Product, ProductDto.class);
    }

    public Product fromProductDtoToProduct(ProductDto ProductDto){
        return mapper.map(ProductDto, Product.class);
    }
}