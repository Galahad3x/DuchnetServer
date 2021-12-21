package duchnet.duchnet;

import duchnet.duchnet.models.Content;
import duchnet.duchnet.models.Description;
import duchnet.duchnet.repository.ContentRepository;
import duchnet.duchnet.repository.DescriptionRepository;
import duchnet.duchnet.repository.FilenameRepository;
import duchnet.duchnet.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class DuchnetService {
    @Autowired
    DescriptionRepository descriptionRepository;
    @Autowired
    FilenameRepository filenameRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    ContentRepository contentRepository;

    public List<Description> findAllFilenames() {
        return filenameRepository.findAll();
    }

    public List<Description> findAllDescriptions() {
        return descriptionRepository.findAll();
    }

    public void postDescription(String hash, String description) {
        Optional<Content> optional = contentRepository.findByHashEquals(hash);
        if (optional.isPresent()) {
            descriptionRepository.save(new Description(optional.get(), description));
        } else {
            Content daContent = new Content(hash);
            contentRepository.save(daContent);
            descriptionRepository.save(new Description(daContent, description));
        }
    }
}
