package namgor.com.namgorstore.dao;

import namgor.com.namgorstore.models.entities.Invoice;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceDao extends ReactiveMongoRepository<Invoice, String> {
}
