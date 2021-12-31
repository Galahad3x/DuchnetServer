package duchnet.duchnet;

import duchnet.duchnet.models.Content;
import duchnet.duchnet.models.Description;
import duchnet.duchnet.models.FileName;
import duchnet.duchnet.models.Tag;
import duchnet.duchnet.repository.ContentRepository;
import duchnet.duchnet.repository.DescriptionRepository;
import duchnet.duchnet.repository.FilenameRepository;
import duchnet.duchnet.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public List<Content> findAllContents() {
        return contentRepository.findAll();
    }

    public Optional<Content> findContentById(Long id) {
        return contentRepository.findById(id);
    }

    public void deleteAllContents() {
        contentRepository.deleteAll();
    }


    public List<FileName> findAllFilenames() {
        return filenameRepository.findAll();
    }

    public List<FileName> findAllFilenames(Long content_id) {
        return filenameRepository.findByContentIdEquals(content_id);
    }

    public void deleteAllFilenames() {
        filenameRepository.deleteAll();
    }

    public Optional<FileName> findFilenameById(Long id) {
        return filenameRepository.findById(id);
    }


    public List<Description> findAllDescriptions() {
        return descriptionRepository.findAll();
    }

    public List<Description> findAllDescriptions(Long content_id) {
        return descriptionRepository.findByContentIdEquals(content_id);
    }

    public void deleteAllDescriptions() {
        descriptionRepository.deleteAll();
    }

    public Optional<Description> findDescriptionById(Long id) {
        return descriptionRepository.findById(id);
    }

    public void postDescription(String hash, String description) {
        Optional<Content> optional = contentRepository.findByHashEquals(hash);
        if (optional.isPresent()) {
            descriptionRepository.save(new Description(optional.get().getId(), description));
        } else {
            Content daContent = new Content(hash);
            contentRepository.save(daContent);
            descriptionRepository.save(new Description(daContent.getId(), description));
        }
    }


    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    public List<Tag> findAllTags(Long content_id) {
        return tagRepository.findByContentIdEquals(content_id);
    }

    public void deleteAllTags() {
        tagRepository.deleteAll();
    }

    public Optional<Tag> findTagById(Long id) {
        return tagRepository.findById(id);
    }
}
