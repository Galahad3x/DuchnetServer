package duchnet.duchnet.models;

import javax.persistence.*;

/**
 * Class to represent a filename in the database
 */
@Entity
public class FileName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long content_id;
    public Long owner_id;

    public Long getContent_id() {
        return content_id;
    }

    private String filename;

    public FileName() {
    }

    public FileName(Long content_id, String filename, Long owner_id){
        this.content_id = content_id;
        this.filename = filename;
        this.owner_id = owner_id;
    }

    public String getFilename() {
        return filename;
    }

    public Long getId() {
        return id;
    }
}
