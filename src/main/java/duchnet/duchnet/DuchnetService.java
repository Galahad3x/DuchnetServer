package duchnet.duchnet;

import duchnet.duchnet.models.*;
import duchnet.duchnet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Objects;
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
    @Autowired
    PeerInfoRepository peerInfoRepository;

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

    public List<FileName> findFilenamesByText(String text) {
        return filenameRepository.findByFilenameContains(text);
    }

    public void postFilename(String hash, String name) {
        Optional<Content> optional = contentRepository.findByHashEquals(hash);
        if (optional.isPresent()) {
            List<FileName> byFilenameContains = filenameRepository.findByFilenameContains(name);
            for (FileName fn : byFilenameContains) {
                if (Objects.equals(fn.getContent_id(), optional.get().getId()) && fn.getFilename().equals(name)) {
                    return;
                }
            }
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

    public void deleteFilenameById(Long id) {
        filenameRepository.deleteById(id);
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
            List<Description> byDescriptionContains = descriptionRepository.findByDescriptionContains(description);
            for (Description fn : byDescriptionContains) {
                if (Objects.equals(fn.getContent_id(), optional.get().getId()) && fn.getDescription().equals(description)) {
                    return;
                }
            }
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

    public void deleteDescriptionById(Long id) {
        descriptionRepository.deleteById(id);
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

    public List<Tag> findTagsByText(String text) {
        return tagRepository.findByTagContains(text);
    }

    public void postTag(String hash, String tag) {
        Optional<Content> optional = contentRepository.findByHashEquals(hash);
        if (optional.isPresent()) {
            List<Tag> byTagContains = tagRepository.findByTagContains(tag);
            for (Tag fn : byTagContains) {
                if (Objects.equals(fn.getContent_id(), optional.get().getId()) && fn.getTag().equals(tag)) {
                    return;
                }
            }
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

    public void deleteTagById(Long id) {
        tagRepository.deleteById(id);
    }

    public void deleteTagsByContentId(Long content_id) {
        tagRepository.deleteByContentId(content_id);
    }

    public List<PeerInfo> findAllPeerInfos(String hash) {
        return peerInfoRepository.findPeersByHash(hash);
    }

    public void postPeer(String hash, String text) {
        Optional<Content> optional = contentRepository.findByHashEquals(hash);
        if (optional.isPresent()) {
            List<PeerInfo> peers = peerInfoRepository.findPeersByHash(optional.get().hash);
            for (PeerInfo peerInfo : peers) {
                if (peerInfo.toString().equals(text)) {
                    return;
                }
            }
            PeerInfo p = PeerInfo.fromString(text);
            p.setHash(hash);
            peerInfoRepository.save(p);
        } else {
            Content daContent = new Content(hash);
            contentRepository.save(daContent);
            PeerInfo p = PeerInfo.fromString(text);
            p.setHash(hash);
            peerInfoRepository.save(p);
        }
    }

    public void deletePeersByHash(String hash) {
        peerInfoRepository.deletePeersByHash(hash);
    }
}
