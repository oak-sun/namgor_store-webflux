package namgor.com.namgorstore.models.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Contractor {
    @Id
    private String id;
    private String name;
    private String passport;
}
