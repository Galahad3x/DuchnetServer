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

/**
 * Manage the calling of the repositories
 */
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

    public Optional<Content> findContentByHash(String hash) {
        return contentRepository.findByHashEquals(hash);
    }

    public void deleteAllContents() {
        contentRepository.deleteAll();
    }

    public void deleteContent(Long id) {
        this.deleteFilenamesByContentId(id);
        this.deleteDescriptionsByContentId(id);
        this.deleteTagsByContentId(id);
        contentRepository.deleteById(id);
    }


    public List<FileName> findAllFilenames() {
        return filenameRepository.findAll();
    }

    public List<FileName> findAllFilenames(Long content_id) {
        return filenameRepository.findByContentIdEquals(content_id);
    }

    public Optional<FileName> findFilenameById(Long id) {
        return filenameRepository.findById(id);
    }

    public List<FileName> findFilenamesByText(String text){
        return filenameRepository.findByFilenameContains(text);
    }

    public void postFilename(String hash, String name) {
        Optional<Content> optional = contentRepository.findByHashEquals(hash);
        if (optional.isPresent()) {
            filenameRepository.save(new FileName(optional.get().getId(), name));
        } else {
            Content daContent = new Content(hash);
            contentRepository.save(daContent);
            filenameRepository.save(new FileName(daContent.getId(), name));
        }
    }

    public void deleteAllFilenames() {
        filenameRepository.deleteAll();
    }

    public void deleteFilenamesByContentId(Long content_id) {
        filenameRepository.deleteByContentId(content_id);
    }


    public List<Description> findAllDescriptions() {
        return descriptionRepository.findAll();
    }

    public List<Description> findAllDescriptions(Long content_id) {
        return descriptionRepository.findByContentIdEquals(content_id);
    }

    public Optional<Description> findDescriptionById(Long id) {
        return descriptionRepository.findById(id);
    }

    public List<Description> findDescriptionsByText(String text) {
        return descriptionRepository.findByDescriptionContains(text);
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

    public void deleteAllDescriptions() {
        descriptionRepository.deleteAll();
    }

    public void deleteDescriptionsByContentId(Long content_id) {
        descriptionRepository.deleteByContentId(content_id);
    }


    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    public List<Tag> findAllTags(Long content_id) {
        return tagRepository.findByContentIdEquals(content_id);
    }

    public Optional<Tag> findTagById(Long id) {
        return tagRepository.findById(id);
    }

    public List<Tag> findTagsByText(String text){
        return tagRepository.findByTagContains(text);
    }

    public void postTag(String hash, String tag) {
        Optional<Content> optional = contentRepository.findByHashEquals(hash);
        if (optional.isPresent()) {
            tagRepository.save(new Tag(optional.get().getId(), tag));
        } else {
            Content daContent = new Content(hash);
            contentRepository.save(daContent);
            tagRepository.save(new Tag(daContent.getId(), tag));
        }
    }

    public void deleteAllTags() {
        tagRepository.deleteAll();
    }

    public void deleteTagsByContentId(Long content_id) {
        tagRepository.deleteByContentId(content_id);
    }
}
