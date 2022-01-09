package duchnet.duchnet.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import duchnet.duchnet.DuchnetService;
import duchnet.duchnet.HashCalculator;
import duchnet.duchnet.common.ContentXML;
import duchnet.duchnet.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController("/v2/contents/search/")
public class SearchEndpoint {

    @Autowired
    DuchnetService duchnetService;

    /**
     * Searching by some type but the type is missing
     *
     * @return ResponseEntity with error message
     */
    @GetMapping("/v2/contents/search/")
    @DeleteMapping("/v2/contents/search/")
    public ResponseEntity<String> searchWithNoType() {
        return new ResponseEntity<>("NO RESOURCE TYPE", HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Use partial search to search for resources
     *
     * @param resource the type of search
     * @param text     the search text
     * @return ResponseEntity with results and a code
     * @throws JsonProcessingException if writing the XML fails
     */
    @GetMapping(value = "/v2/contents/search/{resource}", consumes = {"text/plain"})
    public ResponseEntity<String> findByResource(@PathVariable("resource") String resource, @RequestBody String text) throws JsonProcessingException {
        List<ContentXML> XMLs = new LinkedList<>();
        switch (resource) {
            case "descriptions":
                List<Description> descriptions = duchnetService.findDescriptionsByText(text);
                if (descriptions.size() == 0) {
                    return new ResponseEntity<>("NO CONTENT", HttpStatus.NO_CONTENT);
                }
                for (Description desc : descriptions) {
                    Optional<Content> content = duchnetService.findContentById(desc.getContent_id());
                    boolean found = false;
                    if (content.isPresent()) {
                        for (ContentXML xml : XMLs) {
                            if (content.get().hash.equals(xml.hash)) {
                                xml.description.add(desc.getDescription());
                                found = true;
                            }
                        }
                    }
                    if (!found && content.isPresent()) {
                        XMLs.add(new ContentXML(content.get().hash, new LinkedList<>(), new LinkedList<>(Collections.singletonList(desc.getDescription())), new LinkedList<>()));
                    }
                }
                break;
            case "filenames":
                List<FileName> filenames = duchnetService.findFilenamesByText(text);
                if (filenames.size() == 0) {
                    return new ResponseEntity<>("NO CONTENT", HttpStatus.NO_CONTENT);
                }
                for (FileName name : filenames) {
                    Optional<Content> content = duchnetService.findContentById(name.getContent_id());
                    boolean found = false;
                    for (ContentXML xml : XMLs) {
                        if (content.isPresent()) {
                            if (content.get().hash.equals(xml.hash)) {
                                xml.filename.add(name.getFilename());
                                found = true;
                            }
                        }
                    }
                    if (!found && content.isPresent()) {
                        XMLs.add(new ContentXML(content.get().hash, new LinkedList<>(Collections.singletonList(name.getFilename())), new LinkedList<>(), new LinkedList<>()));
                    }
                }
                break;
            case "tags":
                List<Tag> tags = duchnetService.findTagsByText(text);
                if (tags.size() == 0) {
                    return new ResponseEntity<>("NO CONTENT", HttpStatus.NO_CONTENT);
                }
                for (Tag tg : tags) {
                    Optional<Content> content = duchnetService.findContentById(tg.getContent_id());
                    boolean found = false;
                    if (content.isPresent()) {
                        for (ContentXML xml : XMLs) {
                            if (content.get().hash.equals(xml.hash)) {
                                xml.tag.add(tg.getTag());
                                found = true;
                            }
                        }
                    }
                    if (!found && content.isPresent()) {
                        XMLs.add(new ContentXML(content.get().hash, new LinkedList<>(), new LinkedList<>(), new LinkedList<>(Collections.singletonList(tg.getTag()))));
                    }
                }
                break;
            default:
                return new ResponseEntity<>("RESOURCE TYPE NOT FOUND", HttpStatus.METHOD_NOT_ALLOWED);
        }
        for (ContentXML xml : XMLs) {
            Optional<Content> content_op = duchnetService.findContentByHash(xml.hash);
            if (content_op.isPresent()) {
                Content content = content_op.get();
                for (FileName name : duchnetService.findAllFilenames(content.getId())) {
                    xml.filename.add(name.getFilename());
                }
                for (Description desc : duchnetService.findAllDescriptions(content.getId())) {
                    xml.description.add(desc.getDescription());
                }
                for (Tag tg : duchnetService.findAllTags(content.getId())) {
                    xml.tag.add(tg.getTag());
                }
            }
        }
        return new ResponseEntity<>(new XmlMapper().writeValueAsString(XMLs), HttpStatus.OK);
    }

    /**
     * Use partial search to search for resources and delete them
     *
     * @param resource the type of search
     * @param text     the search text
     * @param username server authentication username
     * @param password server authentication password
     * @return ResponseEntity with results and a code
     */
    @DeleteMapping(value = "/v2/contents/search/{resource}", consumes = {"text/plain"})
    public ResponseEntity<String> deleteBySearch(@PathVariable("resource") String resource, @RequestBody String text, @RequestHeader("username") String username, @RequestHeader("password") String password) {
        User user = new User(username, HashCalculator.getStringHash(password));
        if (!duchnetService.authentify(user)) {
            return new ResponseEntity<>("FAILED AUTHENTICATION", HttpStatus.FORBIDDEN);
        }
        switch (resource) {
            case "descriptions":
                List<Description> descriptions = duchnetService.findDescriptionsByText(text);
                for (Description desc : descriptions) {
                    if (desc.owner_id.equals(user.getId())) {
                        duchnetService.deleteDescriptionById(desc.getId());
                    }
                }
                break;
            case "filenames":
                List<FileName> filenames = duchnetService.findFilenamesByText(text);
                for (FileName name : filenames) {
                    if (name.owner_id.equals(user.getId())) {
                        duchnetService.deleteFilenameById(name.getId());
                    }
                }
                break;
            case "tags":
                List<Tag> tags = duchnetService.findTagsByText(text);
                for (Tag tag : tags) {
                    if (tag.owner_id.equals(user.getId())) {
                        duchnetService.deleteTagById(tag.getId());
                    }
                }
                break;
            case "peers":
                PeerInfo host = PeerInfo.fromString(text);
                if (host.owner_id.equals(user.getId())) {
                    duchnetService.deletePeersByHost(host);
                }
                break;
            default:
                return new ResponseEntity<>("RESOURCE TYPE NOT FOUND", HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);
    }
}
