package duchnet.duchnet.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import duchnet.duchnet.DuchnetService;
import duchnet.duchnet.common.ContentXML;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Handles all the endpoints related to contents
 */
@RestController("/v1/contents/")
public class ContentEndpoint {

    @Autowired
    DuchnetService duchnetService;

    /**
     * Gets all resources given a hash
     * @param hash The hash we are interested in
     * @return A ResponseEntity containing a string representing a ContentXML object in XML and a status code signaling correct execution or not
     * @throws JsonProcessingException If transforming ContentXML to XML fails
     */
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

    /**
     * Deletes all resources related to a hash
     * @param hash The hash we want to delete
     * @return a ResponseEntity of a String and a status code
     */
    @DeleteMapping("/v1/contents/{hash}")
    public ResponseEntity<String> deleteAllResourcesOfHash(@PathVariable("hash") String hash) {
        Optional<Content> the_content = duchnetService.findContentByHash(hash);
        if (the_content.isPresent()) {
            duchnetService.deleteContent(the_content.get().getId());
            return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NON EXISTENT", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get a list of specific resources from a specific hash
     * @param hash The hash we are interested in
     * @param resource The type of resource
     * @return ResponseEntity with a string with a list of the resourceXML and a status code
     * @throws JsonProcessingException If xmlMapper fails
     */
    @GetMapping("/v1/contents/{hash}/{resource}")
    public ResponseEntity<String> getAllSpecificResourcesOfHash(@PathVariable("hash") String hash, @PathVariable("resource") String resource) throws JsonProcessingException {
        Optional<Content> the_content = duchnetService.findContentByHash(hash);
        if (the_content.isPresent()) {
            switch (resource) {
                case "descriptions":
                    List<Description> descriptions = duchnetService.findAllDescriptions(the_content.get().getId());
                    List<String> description = new LinkedList<>();
                    for (Description desc : descriptions) {
                        description.add(desc.getDescription());
                    }
                    return new ResponseEntity<>(new XmlMapper().writeValueAsString(new DescriptionXML(the_content.get().hash, description)), HttpStatus.OK);
                case "filenames":
                    List<FileName> filenames = duchnetService.findAllFilenames(the_content.get().getId());
                    List<String> filename = new LinkedList<>();
                    for (FileName name : filenames) {
                        filename.add(name.getFilename());
                    }
                    return new ResponseEntity<>(new XmlMapper().writeValueAsString(new FilenameXML(the_content.get().hash, filename)), HttpStatus.OK);
                case "tags":
                    List<Tag> tags = duchnetService.findAllTags(the_content.get().getId());
                    List<String> tag = new LinkedList<>();
                    for (Tag tg : tags) {
                        tag.add(tg.getTag());
                    }
                    return new ResponseEntity<>(new XmlMapper().writeValueAsString(new TagXML(the_content.get().hash, tag)), HttpStatus.OK);
                default:
                    return new ResponseEntity<>("RESOURCE TYPE NOT FOUND", HttpStatus.METHOD_NOT_ALLOWED);
            }
        } else {
            return new ResponseEntity<>("EMPTY", HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Post a new resource, if the hash doesn't exist it's created
     * @param hash The hash
     * @param resource The resource type
     * @param text The text of the resource
     * @return ResponseEntity With string and status code
     */
    @PostMapping(value = "/v1/contents/{hash}/{resource}", consumes = {"text/plain"})
    public ResponseEntity<String> postNewResource(@PathVariable("hash") String hash, @PathVariable("resource") String resource, @RequestBody String text) {
        if (text.isBlank()) {
            return new ResponseEntity<>("EMPTY BODY", HttpStatus.BAD_REQUEST);
        }
        switch (resource) {
            case "descriptions":
                duchnetService.postDescription(hash, text);
                break;
            case "filenames":
                duchnetService.postFilename(hash, text);
                break;
            case "tags":
                duchnetService.postTag(hash, text);
                break;
            default:
                return new ResponseEntity<>("RESOURCE TYPE NOT FOUND", HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }

    /**
     * Post a new resource, if the hash doesn't exist it fails
     * @param hash The hash
     * @param resource The resource type
     * @param text The text of the resource
     * @return ResponseEntity With string and status code
     */
    @PutMapping(value = "/v1/contents/{hash}/{resource}", consumes = {"text/plain"})
    public ResponseEntity<String> putNewResource(@PathVariable("hash") String hash, @PathVariable("resource") String resource, @RequestBody String text) {
        Optional<Content> the_content = duchnetService.findContentByHash(hash);
        if (text.isBlank()) {
            return new ResponseEntity<>("EMPTY BODY", HttpStatus.BAD_REQUEST);
        }
        if (the_content.isPresent()) {
            switch (resource) {
                case "descriptions":
                    duchnetService.postDescription(hash, text);
                    break;
                case "filenames":
                    duchnetService.postFilename(hash, text);
                    break;
                case "tags":
                    duchnetService.postTag(hash, text);
                    break;
                default:
                    return new ResponseEntity<>("RESOURCE TYPE NOT FOUND", HttpStatus.METHOD_NOT_ALLOWED);
            }
            return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("HASH NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Self-explanatory name
     * @param hash The hash
     * @param resource The resource type
     * @return ResponseEntity with string and status code
     */
    @DeleteMapping("/v1/contents/{hash}/{resource}")
    public ResponseEntity<String> deleteAllSpecificResourcesOfHash(@PathVariable("hash") String hash, @PathVariable("resource") String resource) {
        Optional<Content> the_content = duchnetService.findContentByHash(hash);
        if (the_content.isPresent()) {
            switch (resource) {
                case "descriptions":
                    duchnetService.deleteDescriptionsByContentId(the_content.get().getId());
                    break;
                case "filenames":
                    duchnetService.deleteFilenamesByContentId(the_content.get().getId());
                    break;
                case "tags":
                    duchnetService.deleteTagsByContentId(the_content.get().getId());
                    break;
                default:
                    return new ResponseEntity<>("RESOURCE TYPE NOT FOUND", HttpStatus.METHOD_NOT_ALLOWED);
            }
            return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("HASH NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }
}
