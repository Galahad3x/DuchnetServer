package duchnet.duchnet.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Description {

    @Id
    private String hash;
    private String description;

    public Description() {
    }

    public Description(String hash, String description){
        this.hash = hash;
        this.description = description;
    }

    public String getHash() {
        return hash;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
