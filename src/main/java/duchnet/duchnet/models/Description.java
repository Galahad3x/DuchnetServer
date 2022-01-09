package duchnet.duchnet.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Description {

    public Long owner_id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long content_id;
    private String description;

    public Description() {
    }

    public Description(Long content_id, String description, Long owner_id) {
        this.content_id = content_id;
        this.description = description;
        this.owner_id = owner_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getContent_id() {
        return content_id;
    }

    public Long getId() {
        return id;
    }
}
