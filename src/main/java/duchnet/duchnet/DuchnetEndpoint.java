package duchnet.duchnet;

import duchnet.duchnet.models.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/")
public class DuchnetEndpoint {

    @Autowired
    DuchnetService duchnetService;

    @GetMapping(value = "/", produces = {MediaType.TEXT_XML_VALUE})
    public List<Description> hello() {
        return duchnetService.findAllDescriptions();
    }

    @PostMapping("/")
    public String postDescription(@PathVariable("hash") String hash, @RequestBody String description) {
        duchnetService.postDescription(hash, description);
        return "Description posted";
    }
}
