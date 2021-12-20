package duchnet.duchnet.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FileName {
    @Id
    private String hash;
    private String filename;

    public String getHash() {
        return hash;
    }

    public String getFilename() {
        return filename;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
