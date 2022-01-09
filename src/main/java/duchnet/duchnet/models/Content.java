package duchnet.duchnet.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Class to represent a content in the database
 */
@Entity
public class Content {

    public String hash;
    public Long owner_id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Content() {
    }

    public Content(String hash) {
        this.hash = hash;
    }

    public Long getId() {
        return id;
    }
}
