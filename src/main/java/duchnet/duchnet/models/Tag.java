package duchnet.duchnet.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Class to represent a tag in the database
 */
@Entity
public class Tag {

    public Long owner_id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long content_id;
    private String tag;

    public Tag() {
    }

    public Tag(Long content_id, String tag, Long owner_id) {
        this.content_id = content_id;
        this.tag = tag;
        this.owner_id = owner_id;
    }

    public Long getContent_id() {
        return content_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }
}
