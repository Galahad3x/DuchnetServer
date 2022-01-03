package duchnet.duchnet.models;

import javax.persistence.*;

@Entity
public class Description {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long content_id;

    private String description;

    public Description() {
    }

    public Description(Long content_id, String description){
        this.content_id = content_id;
        this.description = description;
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
}
