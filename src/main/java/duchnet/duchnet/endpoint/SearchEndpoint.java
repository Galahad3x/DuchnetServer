package duchnet.duchnet.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1/contents/search/")
public class SearchEndpoint {

    @GetMapping("/v1/contents/search/")
    @DeleteMapping("/v1/contents/search/")
    public ResponseEntity<String> searchWithNoType(){
        return new ResponseEntity<>("NO RESOURCE TYPE", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping(value = "/v1/contents/search/{resource}", consumes = {"text/plain"})
    public ResponseEntity<String> findByResource(@PathVariable("resource") String resource, ){

    }
}
