package duchnet.duchnet.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import duchnet.duchnet.DuchnetService;
import duchnet.duchnet.common.ContentXML;
import duchnet.duchnet.common.DescriptionXML;
import duchnet.duchnet.models.Content;
import duchnet.duchnet.models.Description;
import duchnet.duchnet.models.FileName;
import duchnet.duchnet.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController("/v1/resources")
public class ResourceEndpoint {

    @Autowired
    private DuchnetService duchnetService;

    @GetMapping("/")
    public ResponseEntity<String> getEverything() throws JsonProcessingException {
        return new EverythingEndpoint().getEverything();
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteEverything() {
        return new EverythingEndpoint().deleteEverything();
    }

    @GetMapping("/{resource}")
    public ResponseEntity<String> getResource(@PathVariable("resource") String resource) throws JsonProcessingException {
        switch (resource) {
            case "filenames": {
                List<Content> contents = duchnetService.findAllContents();
                List<DescriptionXML> filenameXMLS = new LinkedList<>();
                for (Content content : contents) {
                    List<String> filename = new LinkedList<>();
                    for (FileName name : duchnetService.findAllFilenames(content.getId())) {
                        filename.add(name.getFilename());
                    }
                    filenameXMLS.add(new DescriptionXML(content.hash, filename));
                }
                return new ResponseEntity<>(new XmlMapper().writeValueAsString(filenameXMLS), HttpStatus.OK);
            }
            case "descriptions": {
                List<Content> contents = duchnetService.findAllContents();
                List<DescriptionXML> descriptionXMLS = new LinkedList<>();
                for (Content content : contents) {
                    List<String> description = new LinkedList<>();
                    for (Description desc : duchnetService.findAllDescriptions(content.getId())) {
                        description.add(desc.getDescription());
                    }
                    descriptionXMLS.add(new DescriptionXML(content.hash, description));
                }
                return new ResponseEntity<>(new XmlMapper().writeValueAsString(descriptionXMLS), HttpStatus.OK);
            }
            case "tags": {
                List<Content> contents = duchnetService.findAllContents();
                List<DescriptionXML> tagsXMLS = new LinkedList<>();
                for (Content content : contents) {
                    List<String> tag = new LinkedList<>();
                    for (Tag tg : duchnetService.findAllTags(content.getId())) {
                        tag.add(tg.getTag());
                    }
                    tagsXMLS.add(new DescriptionXML(content.hash, tag));
                }
                return new ResponseEntity<>(new XmlMapper().writeValueAsString(tagsXMLS), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("RESOURCE TYPE NOT FOUND", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping("/{resource}")
    public ResponseEntity<String> deleteResource(@PathVariable("resource") String resource){
        switch (resource) {
            case "filenames": {
                duchnetService.deleteAllFilenames();
                return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);
            }
            case "descriptions": {
                duchnetService.deleteAllDescriptions();
                return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);
            }
            case "tags": {
                duchnetService.deleteAllTags();
                return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("RESOURCE TYPE NOT FOUND", HttpStatus.METHOD_NOT_ALLOWED);
    }
}