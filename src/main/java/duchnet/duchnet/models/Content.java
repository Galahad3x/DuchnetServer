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

    @OneToMany(mappedBy = "filename", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FileName> filenames;
    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Description> descriptions;
    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Tag> tags;
}
