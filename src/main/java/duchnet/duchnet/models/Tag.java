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

    public Long getContent_id() {
        return content_id;
    }

    private String tag;

    public Tag() {
    }

    public Tag(Long content_id, String tag) {
        this.content_id = content_id;
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
