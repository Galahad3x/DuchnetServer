package duchnet.duchnet.endpoint;

import duchnet.duchnet.DuchnetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Basic API mapping
 */
@RestController("/")
public class DuchnetEndpoint {

    @Autowired
    DuchnetService duchnetService;

    /**
     * Custom /error mapping
     * @return ResponseEntity with error status code
     */
    @GetMapping("/error")
    public ResponseEntity<String> error(){
        return new ResponseEntity<>("Something went wrong", HttpStatus.NOT_ACCEPTABLE);
    }
}
