package duchnet.duchnet.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tag {

    @Id
    private String hash;
    private String tag;

    public String getHash() {
        return hash;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}

