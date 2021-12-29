package duchnet.duchnet.models;

import javax.persistence.*;
import java.util.List;

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
