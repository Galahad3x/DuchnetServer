package duchnet.duchnet.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "duchnetUser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password_hash;

    public User() {

    }

    public User(String username, String password_hash) {
        this.username = username;
        this.password_hash = password_hash;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String old_password_hash, String new_password_hash) {
        if (old_password_hash.equals(password_hash)) {
            this.password_hash = new_password_hash;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
