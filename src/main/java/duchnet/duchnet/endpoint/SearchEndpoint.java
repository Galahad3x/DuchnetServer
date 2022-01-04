package duchnet.duchnet.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import duchnet.duchnet.DuchnetService;
import duchnet.duchnet.common.DescriptionXML;
import duchnet.duchnet.common.FilenameXML;
import duchnet.duchnet.common.TagXML;
import duchnet.duchnet.models.Content;
import duchnet.duchnet.models.Description;
import duchnet.duchnet.models.FileName;
import duchnet.duchnet.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController("/v1/contents/search/")
public class SearchEndpoint {

    @Autowired
    DuchnetService duchnetService;

    @GetMapping("/v1/contents/search/")
    @DeleteMapping("/v1/contents/search/")
    public ResponseEntity<String> searchWithNoType(){
        return new ResponseEntity<>("NO RESOURCE TYPE", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping(value = "/v1/contents/search/{resource}", consumes = {"text/plain"})
    public ResponseEntity<String> findByResource(@PathVariable("resource") String resource, @RequestBody String text) throws JsonProcessingException {
        // TODO Canviar per a que retorni ContentXML i no especific
        switch (resource){
            case "descriptions":
                List<Description> descriptions = duchnetService.findDescriptionsByText(text);
                List<DescriptionXML> XMLs = new LinkedList<>();
                if (descriptions.size() == 0){
                    return new ResponseEntity<>("NO CONTENT", HttpStatus.NO_CONTENT);
                }
                for (Description desc : descriptions){
                    Optional<Content> content = duchnetService.findContentById(desc.getContent_id());
                    boolean found = false;
                    for (DescriptionXML descXML : XMLs){
                        if (content.isPresent()){
                            if (content.get().hash.equals(descXML.hash)){
                                descXML.description.add(desc.getDescription());
                                found = true;
                            }
                        }
                    }
                    if (!found && content.isPresent()){
                        XMLs.add(new DescriptionXML(content.get().hash, new LinkedList<>(Collections.singletonList(desc.getDescription()))));
                    }
                }
                return new ResponseEntity<>(new XmlMapper().writeValueAsString(XMLs), HttpStatus.OK);
            case "filenames":
                List<FileName> filenames = duchnetService.findFilenamesByText(text);
                List<FilenameXML> fXMLs = new LinkedList<>();
                if (filenames.size() == 0){
                    return new ResponseEntity<>("NO CONTENT", HttpStatus.NO_CONTENT);
                }
                for (FileName name : filenames){
                    Optional<Content> content = duchnetService.findContentById(name.getContent_id());
                    boolean found = false;
                    for (FilenameXML nameXML : fXMLs){
                        if (content.isPresent()){
                            if (content.get().hash.equals(nameXML.hash)){
                                nameXML.filename.add(name.getFilename());
                                found = true;
                            }
                        }
                    }
                    if (!found && content.isPresent()){
                        fXMLs.add(new FilenameXML(content.get().hash, new LinkedList<>(Collections.singletonList(name.getFilename()))));
                    }
                }
                return new ResponseEntity<>(new XmlMapper().writeValueAsString(fXMLs), HttpStatus.OK);
            case "tags":
                List<Tag> tags = duchnetService.findTagsByText(text);
                List<TagXML> tXMLs = new LinkedList<>();
                if (tags.size() == 0){
                    return new ResponseEntity<>("NO CONTENT", HttpStatus.NO_CONTENT);
                }
                for (Tag tg : tags){
                    Optional<Content> content = duchnetService.findContentById(tg.getContent_id());
                    boolean found = false;
                    for (TagXML tXML : tXMLs){
                        if (content.isPresent()){
                            if (content.get().hash.equals(tXML.hash)){
                                tXML.tag.add(tg.getTag());
                                found = true;
                            }
                        }
                    }
                    if (!found && content.isPresent()){
                        tXMLs.add(new TagXML(content.get().hash, new LinkedList<>(Collections.singletonList(tg.getTag()))));
                    }
                }
                return new ResponseEntity<>(new XmlMapper().writeValueAsString(tXMLs), HttpStatus.OK);
            default:
                return new ResponseEntity<>("RESOURCE TYPE NOT FOUND", HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
