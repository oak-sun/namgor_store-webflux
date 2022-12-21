package namgor.com.namgorstore.dao;

import namgor.com.namgorstore.models.entities.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends ReactiveMongoRepository<Product, String> {
}
