package duchnet.duchnet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import duchnet.duchnet.common.DescriptionXML;
import duchnet.duchnet.models.Content;
import duchnet.duchnet.models.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController("/")
public class DuchnetEndpoint {

    @Autowired
    DuchnetService duchnetService;

    @GetMapping(value = "/", produces = {"text/plain"})
    public ResponseEntity<String> hello() throws JsonProcessingException {
        List<Description> descriptions = duchnetService.findAllDescriptions();
        List<DescriptionXML> descriptionReturns = new LinkedList<>();
        for (Description desc : descriptions) {
            Optional<Content> daContent = duchnetService.findContentById(desc.getContent_id());
            if (daContent.isPresent()) {
                boolean found = false;
                for (DescriptionXML descXML : descriptionReturns) {
                    if (descXML.hash.equals(daContent.get().hash)) {
                        descXML.descriptions.add(desc.getDescription());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    descriptionReturns.add(new DescriptionXML(daContent.get().hash, new LinkedList<>(Collections.singleton(desc.getDescription()))));
                }
            }
        }
        return new ResponseEntity<>(new XmlMapper().writeValueAsString(descriptionReturns), HttpStatus.CREATED);
    }

    @PostMapping(value = "/hash/{hash}", consumes = {"text/plain"})
    public String postDescription(@PathVariable("hash") String hash, @RequestBody String description) {
        System.out.println(description);
        duchnetService.postDescription(hash, description);
        return "Description posted";
    }
}
