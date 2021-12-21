package duchnet.duchnet;

import duchnet.duchnet.models.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
public class DuchnetEndpoint {

    @Autowired
    DuchnetService duchnetService;

    @GetMapping("/")
    public List<Description> hello(){
        return duchnetService.findAllDescriptions();
    }

    @GetMapping("/post")
    public String postDescription(){
        duchnetService.postDescription();
        return "Description posted";
    }
}
