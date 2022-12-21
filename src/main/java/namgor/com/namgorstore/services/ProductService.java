package namgor.com.namgorstore.services;

import lombok.AllArgsConstructor;
import namgor.com.namgorstore.models.dto.ProductDto;
import namgor.com.namgorstore.models.mapper.ProductMapper;
import namgor.com.namgorstore.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ProductService {

    @Autowired
    private ProductDao dao;

    @Autowired
    private ProductMapper mapper;

    public Flux<ProductDto> getAllProducts() {
        return dao
                .findAll()
                .map(mapper::fromProductToProductDto);
    }

    public Mono<ProductDto> postProduct(ProductDto dto) {
        return dao
                .save(mapper.fromProductDtoToProduct(dto))
                .map(mapper::fromProductToProductDto);
    }

    public Mono<ProductDto> putProduct(ProductDto dto) {
        return dao
                .save(mapper.fromProductDtoToProduct(dto))
                .map(mapper::fromProductToProductDto);
    }

    public Mono<Void> deleteProductById(String id) {
        return dao
                .findById(id)
                .switchIfEmpty(Mono.error(
                        new IllegalStateException(
                                "Product with id:" + id + " doesn't exist")))
                .flatMap(product -> dao.deleteById(product.getId()));
    }

}
