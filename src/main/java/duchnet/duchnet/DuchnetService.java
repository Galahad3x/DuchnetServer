package duchnet.duchnet;

import duchnet.duchnet.models.Description;
import duchnet.duchnet.repository.DescriptionRepository;
import duchnet.duchnet.repository.FilenameRepository;
import duchnet.duchnet.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DuchnetService {
    @Autowired
    DescriptionRepository descriptionRepository;
    @Autowired
    FilenameRepository filenameRepository;
    @Autowired
    TagRepository tagRepository;

    public List<Description> findAllFilenames(){
        return filenameRepository.findAll();
    }
}
