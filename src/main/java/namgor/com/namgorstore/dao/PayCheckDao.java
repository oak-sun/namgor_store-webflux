package namgor.com.namgorstore.dao;

import namgor.com.namgorstore.models.entities.PayCheck;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PayCheckDao extends ReactiveMongoRepository<PayCheck, String> {
}
