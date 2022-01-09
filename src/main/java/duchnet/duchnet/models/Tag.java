package duchnet.duchnet.models;

import javax.persistence.*;

/**
 * Class to represent a tag in the database
 */
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long content_id;
    public Long owner_id;

    public Long getContent_id() {
        return content_id;
    }

    private String tag;

    public Tag() {
    }

    public Tag(Long content_id, String tag, Long owner_id) {
        this.content_id = content_id;
        this.tag = tag;
        this.owner_id = owner_id;
    }

    public String getTag() {
        return tag;
    }

    public Long getId() {
        return id;
    }
}
