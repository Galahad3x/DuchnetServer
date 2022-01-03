package duchnet.duchnet.models;

import javax.persistence.*;

/**
 * Class to represent a content in the database
 */
@Entity
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String hash;

    public Content() {
    }

    public Content(String hash) {
        this.hash = hash;
    }

    public Long getId() {
        return id;
    }
}
