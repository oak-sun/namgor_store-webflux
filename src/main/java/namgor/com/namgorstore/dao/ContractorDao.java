package namgor.com.namgorstore.dao;

import namgor.com.namgorstore.models.entities.Contractor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorDao extends ReactiveMongoRepository<Contractor, String> {
}
