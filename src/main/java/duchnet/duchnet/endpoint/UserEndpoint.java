package duchnet.duchnet.endpoint;

import duchnet.duchnet.DuchnetService;
import duchnet.duchnet.HashCalculator;
import duchnet.duchnet.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/v2/auth")
public class UserEndpoint {

    @Autowired
    DuchnetService duchnetService;

    @PostMapping("/v2/auth")
    public ResponseEntity<String> createUser(@RequestHeader("username") String username, @RequestHeader("password") String password) {
        User user = new User(username, HashCalculator.getStringHash(password));
        if(duchnetService.createUser(user)){
            return new ResponseEntity<>("CREATED SUCCESSFULLY", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("USERNAME TAKEN", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/v2/auth")
    public ResponseEntity<String> changePassword(@RequestHeader("username") String username, @RequestHeader("password") String password, @RequestHeader("new-password") String new_password) {
        User user = new User(username, HashCalculator.getStringHash(password));
        if(duchnetService.modifyUser(user, new_password)){
            return new ResponseEntity<>("CHANGED SUCCESSFULLY", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("UNSUCCESSFUL IDENTIFICATION", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/v2/auth")
    public ResponseEntity<String> deleteUser(@RequestHeader("username") String username, @RequestHeader("password") String password) {
        User user = new User(username, HashCalculator.getStringHash(password));
        if(duchnetService.deleteUser(user)){
            return new ResponseEntity<>("DELETED SUCCESSFULLY", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("UNSUCCESSFUL IDENTIFICATION", HttpStatus.FORBIDDEN);
        }
    }
}
