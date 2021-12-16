package duchnet.duchnet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class DuchnetEndpoint {

    @GetMapping("/")
    public String hello(){
        return "<h1>Hello world!</h1>";
    }
}
