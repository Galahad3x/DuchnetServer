package duchnet.duchnet.models;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password_hash;

    public User(){

    }

    public User(String username, String password_hash){
        this.username = username;
        this.password_hash = password_hash;
    }
}
