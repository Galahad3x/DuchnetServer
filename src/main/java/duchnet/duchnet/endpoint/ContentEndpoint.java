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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController("/v1/contents/")
public class ContentEndpoint {

    @Autowired
    DuchnetService duchnetService;

    @GetMapping("/v1/contents/{hash}")
    public ResponseEntity<String> getAllResourcesOfHash(@PathVariable("hash") String hash) throws JsonProcessingException {
        Optional<Content> the_content = duchnetService.findContentByHash(hash);
        if (the_content.isPresent()) {
            Content content = the_content.get();
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
            ContentXML contentXML = new ContentXML(content.hash, filename, description, tag);
            return new ResponseEntity<>(new XmlMapper().writeValueAsString(contentXML), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("EMPTY", HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/v1/contents/{hash}")
    public ResponseEntity<String> deleteAllResourcesOfHash(@PathVariable("hash") String hash){
        Optional<Content> the_content = duchnetService.findContentByHash(hash);
        if (the_content.isPresent()) {
            duchnetService.deleteContent(the_content.get().getId());
            return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("NON EXISTENT", HttpStatus.BAD_REQUEST);
        }
    }
}
