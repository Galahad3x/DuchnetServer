package duchnet.duchnet.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import duchnet.duchnet.DuchnetService;
import duchnet.duchnet.HashCalculator;
import duchnet.duchnet.common.DescriptionXML;
import duchnet.duchnet.common.FilenameXML;
import duchnet.duchnet.common.TagXML;
import duchnet.duchnet.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Endpoint that maps everything related to resources
 */
@RestController("/v3/resources")
public class ResourceEndpoint {

    @Autowired
    private DuchnetService duchnetService;

    /**
     * Get everything from the database
     *
     * @return ResponseEntity with a string representing a List of ContentXMLs in XML and a status code
     * @throws JsonProcessingException If xmlMapper fails
     */
    @GetMapping("/v3/resources/")
    public ResponseEntity<String> getEverything() throws JsonProcessingException {
        return new EverythingEndpoint().getEverything();
    }

    /**
     * Delete everything
     *
     * @param username server authentication username
     * @param password server authentication password
     * @return ResponseEntity with a string and status code
     */
    @DeleteMapping("/v3/resources/")
    public ResponseEntity<String> deleteEverything(@RequestHeader("username") String username, @RequestHeader("password") String password) {
        return new EverythingEndpoint().deleteEverything(username, password);
    }

    /**
     * Get all the resources of a specific type
     *
     * @param resource The resource type
     * @return ResponseEntity with a string with the List of ResourceXMLs in XML and a status code
     * @throws JsonProcessingException If xmlMapper fails
     */
    @GetMapping("/v3/resources/{resource}")
    public ResponseEntity<String> getResource(@PathVariable("resource") String resource) throws JsonProcessingException {
        switch (resource) {
            case "descriptions":
                List<Description> descriptions = duchnetService.findAllDescriptions();
                List<DescriptionXML> XMLs = new LinkedList<>();
                if (descriptions.size() == 0) {
                    return new ResponseEntity<>("NO CONTENT", HttpStatus.NO_CONTENT);
                }
                for (Description desc : descriptions) {
                    Optional<Content> content = duchnetService.findContentById(desc.getContent_id());
                    content.ifPresent(value -> XMLs.add(new DescriptionXML(value.hash, desc.getId(), new LinkedList<>(Collections.singletonList(desc.getDescription())))));
                }
                return new ResponseEntity<>(new XmlMapper().writeValueAsString(XMLs), HttpStatus.OK);
            case "filenames":
                List<FileName> filenames = duchnetService.findAllFilenames();
                List<FilenameXML> fXMLs = new LinkedList<>();
                if (filenames.size() == 0) {
                    return new ResponseEntity<>("NO CONTENT", HttpStatus.NO_CONTENT);
                }
                for (FileName name : filenames) {
                    Optional<Content> content = duchnetService.findContentById(name.getContent_id());
                    content.ifPresent(value -> fXMLs.add(new FilenameXML(value.hash, name.getId(), new LinkedList<>(Collections.singletonList(name.getFilename())))));
                }
                return new ResponseEntity<>(new XmlMapper().writeValueAsString(fXMLs), HttpStatus.OK);
            case "tags":
                List<Tag> tags = duchnetService.findAllTags();
                List<TagXML> tXMLs = new LinkedList<>();
                if (tags.size() == 0) {
                    return new ResponseEntity<>("NO CONTENT", HttpStatus.NO_CONTENT);
                }
                for (Tag tg : tags) {
                    Optional<Content> content = duchnetService.findContentById(tg.getContent_id());
                    content.ifPresent(value -> tXMLs.add(new TagXML(value.hash, tg.getId(), new LinkedList<>(Collections.singletonList(tg.getTag())))));
                }
                return new ResponseEntity<>(new XmlMapper().writeValueAsString(tXMLs), HttpStatus.OK);
            default:
                return new ResponseEntity<>("RESOURCE TYPE NOT FOUND", HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    /**
     * Delete all the resources of a specific type owned by a user
     *
     * @param resource The resource type
     * @param username server authentication username
     * @param password server authentication password
     * @return ResponseEntity with a string and a status code
     */
    @DeleteMapping("/v3/resources/{resource}")
    public ResponseEntity<String> deleteResource(@PathVariable("resource") String resource, @RequestHeader("username") String username, @RequestHeader("password") String password) {
        User user = new User(username, HashCalculator.getStringHash(password));
        if (!duchnetService.authentify(user)) {
            return new ResponseEntity<>("FAILED AUTHENTICATION", HttpStatus.FORBIDDEN);
        }
        switch (resource) {
            case "filenames": {
                duchnetService.deleteAllFilenames(user);
                return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);
            }
            case "descriptions": {
                duchnetService.deleteAllDescriptions(user);
                return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);
            }
            case "tags": {
                duchnetService.deleteAllTags(user);
                return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("RESOURCE TYPE NOT FOUND", HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Get a resource given a type and an ID
     *
     * @param resource The resource type
     * @param id_s     The given id, in String format
     * @return ResponseEntity with a string with the ResourceXML in XML and a status code
     * @throws JsonProcessingException If xmlMapper fails
     */
    @GetMapping("/v3/resources/{resource}/{id}")
    public ResponseEntity<String> getResourceById(@PathVariable("resource") String resource, @PathVariable("id") String id_s) throws JsonProcessingException {
        Long id = Long.parseLong(id_s);
        switch (resource) {
            case "descriptions":
                Optional<Description> desc = duchnetService.findDescriptionById(id);
                if (desc.isPresent()) {
                    Optional<Content> content = duchnetService.findContentById(desc.get().getContent_id());
                    if (content.isPresent()) {
                        return new ResponseEntity<>(new XmlMapper().writeValueAsString(
                                new DescriptionXML(content.get().hash, desc.get().getId(), Collections.singletonList(desc.get().getDescription()))
                        ), HttpStatus.OK);
                    }
                }
                return new ResponseEntity<>(new XmlMapper().writeValueAsString("NOT FOUND"), HttpStatus.NOT_FOUND);
            case "filenames":
                Optional<FileName> fname = duchnetService.findFilenameById(id);
                if (fname.isPresent()) {
                    Optional<Content> content = duchnetService.findContentById(fname.get().getContent_id());
                    if (content.isPresent()) {
                        return new ResponseEntity<>(new XmlMapper().writeValueAsString(
                                new FilenameXML(content.get().hash, fname.get().getId(), Collections.singletonList(fname.get().getFilename()))
                        ), HttpStatus.OK);
                    }
                }
                return new ResponseEntity<>(new XmlMapper().writeValueAsString("NOT FOUND"), HttpStatus.NOT_FOUND);
            case "tags":
                Optional<Tag> tg = duchnetService.findTagById(id);
                if (tg.isPresent()) {
                    Optional<Content> content = duchnetService.findContentById(tg.get().getContent_id());
                    if (content.isPresent()) {
                        return new ResponseEntity<>(new XmlMapper().writeValueAsString(
                                new TagXML(content.get().hash, tg.get().getId(), Collections.singletonList(tg.get().getTag()))
                        ), HttpStatus.OK);
                    }
                }
                return new ResponseEntity<>(new XmlMapper().writeValueAsString("NOT FOUND"), HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity<>("RESOURCE TYPE NOT FOUND", HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    /**
     * Modify a resource given a type, an ID and the new text
     *
     * @param resource The resource type
     * @param id_s     The given id, in String format
     * @param body     The body of the request with the new resource
     * @return ResponseEntity with a string with the ResourceXML in XML and a status code
     * @throws JsonProcessingException If xmlMapper fails
     */
    @PutMapping("/v3/resources/{resource}/{id}")
    public ResponseEntity<String> modifyResourceById(@PathVariable("resource") String resource,
                                                     @PathVariable("id") String id_s,
                                                     @RequestBody String body,
                                                     @RequestHeader("username") String username,
                                                     @RequestHeader("password") String password) throws JsonProcessingException {
        Long id = Long.parseLong(id_s);
        User user = new User(username, HashCalculator.getStringHash(password));
        if (!duchnetService.authentify(user)) {
            return new ResponseEntity<>("FAILED AUTHENTICATION", HttpStatus.FORBIDDEN);
        }
        if (body.isBlank()) {
            return new ResponseEntity<>("EMPTY BODY", HttpStatus.BAD_REQUEST);
        }
        switch (resource) {
            case "descriptions":
                Optional<Description> desc = duchnetService.findDescriptionById(id);
                if (desc.isPresent()) {
                    if (desc.get().owner_id.equals(user.getId())) {
                        Description d = desc.get();
                        d.setDescription(body);
                        duchnetService.saveDescription(d);
                        return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("FORBIDDEN ACCESS", HttpStatus.FORBIDDEN);
                    }
                }
                return new ResponseEntity<>(new XmlMapper().writeValueAsString("NOT FOUND"), HttpStatus.NOT_FOUND);
            case "tags":
                Optional<Tag> tag = duchnetService.findTagById(id);
                if (tag.isPresent()) {
                    if (tag.get().owner_id.equals(user.getId())) {
                        Tag t = tag.get();
                        t.setTag(body);
                        duchnetService.saveTag(t);
                        return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("FORBIDDEN ACCESS", HttpStatus.FORBIDDEN);
                    }
                }
                return new ResponseEntity<>(new XmlMapper().writeValueAsString("NOT FOUND"), HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity<>("RESOURCE TYPE NOT FOUND", HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    /**
     * Modify a resource given a type, an ID and the new text
     *
     * @param resource The resource type
     * @param id_s     The given id, in String format
     * @return ResponseEntity with a string with the ResourceXML in XML and a status code
     * @throws JsonProcessingException If xmlMapper fails
     */
    @DeleteMapping("/v3/resources/{resource}/{id}")
    public ResponseEntity<String> deleteResourceById(@PathVariable("resource") String resource,
                                                     @PathVariable("id") String id_s,
                                                     @RequestHeader("username") String username,
                                                     @RequestHeader("password") String password) throws JsonProcessingException {
        Long id = Long.parseLong(id_s);
        User user = new User(username, HashCalculator.getStringHash(password));
        if (!duchnetService.authentify(user)) {
            return new ResponseEntity<>("FAILED AUTHENTICATION", HttpStatus.FORBIDDEN);
        }
        switch (resource) {
            case "descriptions":
                Optional<Description> desc = duchnetService.findDescriptionById(id);
                if (desc.isPresent()) {
                    if (desc.get().owner_id.equals(user.getId())) {
                        duchnetService.deleteDescriptionById(id);
                        return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("FORBIDDEN ACCESS", HttpStatus.FORBIDDEN);
                    }
                }
                return new ResponseEntity<>(new XmlMapper().writeValueAsString("NOT FOUND"), HttpStatus.NOT_FOUND);
            case "tags":
                Optional<Tag> tag = duchnetService.findTagById(id);
                if (tag.isPresent()) {
                    if (tag.get().owner_id.equals(user.getId())) {
                        duchnetService.deleteTagById(id);
                        return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("FORBIDDEN ACCESS", HttpStatus.FORBIDDEN);
                    }
                }
                return new ResponseEntity<>(new XmlMapper().writeValueAsString("NOT FOUND"), HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity<>("RESOURCE TYPE NOT FOUND", HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}