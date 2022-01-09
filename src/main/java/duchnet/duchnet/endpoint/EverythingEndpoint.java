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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * Endpoint to map the base URLs, which get or delete everything
 */
@RestController("/v2")
public class EverythingEndpoint {

    @Autowired
    private DuchnetService duchnetService;

    /**
     * Get everything from the database
     *
     * @return ResponseEntity with a string representing a List of ContentXMLs in XML and a status code
     * @throws JsonProcessingException If xmlMapper fails
     */
    @GetMapping("/v2/")
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

    /**
     * Delete everything
     *
     * @param username server authentication username
     * @param password server authentication password
     * @return new ResponseEntity<>("SUCCESSFUL", HttpStatus. OK);
     */
    @DeleteMapping("/v2/")
    public ResponseEntity<String> deleteEverything(@RequestHeader("username") String username, @RequestHeader("password") String password) {
        User user = new User(username, HashCalculator.getStringHash(password));
        if (!duchnetService.authentify(user)) {
            return new ResponseEntity<>("FAILED AUTHENTICATION", HttpStatus.FORBIDDEN);
        }
        duchnetService.deleteAllFilenames(user);
        duchnetService.deleteAllDescriptions(user);
        duchnetService.deleteAllTags(user);
        duchnetService.deleteAllPeers(user);
        return new ResponseEntity<>("SUCCESSFUL", HttpStatus.OK);
    }
}
