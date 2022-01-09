package duchnet.duchnet.endpoint;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v2/auth")
public class UserEndpoint {
    @PostMapping("/v2/auth")
    public void createUser(){

    }

    @PutMapping("/v2/auth")
    public void changePassword(){

    }

    @DeleteMapping("/v2/auth")
    public void deleteUser(){

    }
}
