package duchnet.duchnet.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import duchnet.duchnet.DuchnetService;
import duchnet.duchnet.common.ContentXML;
import duchnet.duchnet.models.Content;
import duchnet.duchnet.models.Description;
import duchnet.duchnet.models.FileName;
import duchnet.duchnet.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController("/v1")
public class EverythingEndpoint {

    @Autowired
    private DuchnetService duchnetService;

    @GetMapping("/")
    public ResponseEntity<String> getEverything() throws JsonProcessingException {
        List<Content> contents = duchnetService.findAllContents();
        List<ContentXML> contentXMLS = new LinkedList<>();
        for (Content content : contents) {
            List<String> filename = new LinkedList<>();
            for (FileName name : duchnetService.findAllFilenames(content.getId())) {
                filename.add(name.getFilename());
            }
            List<String> description = new LinkedList<>();
            for (Description desc : duchnetService.findAllDescriptions(content.getId())) {
                description.add(desc.getDescription());
            }
            List<String> tag = new LinkedList<>();
            for (Tag tg : duchnetService.findAllTags(content.getId())) {
                tag.add(tg.getTag());
            }
            contentXMLS.add(new ContentXML(content.hash, filename, description, tag));
        }
        return new ResponseEntity<>(new XmlMapper().writeValueAsString(contentXMLS), HttpStatus.OK);
    }

    public ResponseEntity<String> deleteEverything(){
        duchnetService.deleteAllContents();
        duchnetService.deleteAllFilenames();
        duchnetService.deleteAllDescriptions();
        duchnetService.deleteAllTags();
        return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);
    }
}
