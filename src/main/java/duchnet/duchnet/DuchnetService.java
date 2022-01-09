package duchnet.duchnet;

import duchnet.duchnet.models.*;
import duchnet.duchnet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    @Autowired
    UserRepository userRepository;

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

    public void deleteContent(Long id, User user) {
        this.deleteFilenamesByContentId(id, user);
        this.deleteDescriptionsByContentId(id, user);
        this.deleteTagsByContentId(id, user);
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

    public void postFilename(String hash, String name, User user) {
        Optional<Content> optional = contentRepository.findByHashEquals(hash);
        if (optional.isPresent()) {
            List<FileName> byFilenameContains = filenameRepository.findByFilenameContains(name);
            for (FileName fn : byFilenameContains) {
                if (Objects.equals(fn.getContent_id(), optional.get().getId()) && fn.getFilename().equals(name) && fn.owner_id.equals(user.getId())) {
                    return;
                }
            }
            filenameRepository.save(new FileName(optional.get().getId(), name, user.getId()));
        } else {
            Content daContent = new Content(hash);
            contentRepository.save(daContent);
            filenameRepository.save(new FileName(daContent.getId(), name, user.getId()));
        }
    }

    public void deleteAllFilenames() {
        filenameRepository.deleteAll();
    }

    public void deleteAllFilenames(User user) {
        filenameRepository.deleteAllByOwnerId(user.getId());
    }

    public void deleteFilenameById(Long id) {
        filenameRepository.deleteById(id);
    }

    public void deleteFilenamesByContentId(Long content_id, User user) {
        filenameRepository.deleteByContentId(content_id, user.getId());
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

    public void postDescription(String hash, String description, User user) {
        Optional<Content> optional = contentRepository.findByHashEquals(hash);
        if (optional.isPresent()) {
            List<Description> byDescriptionContains = descriptionRepository.findByDescriptionContains(description);
            for (Description fn : byDescriptionContains) {
                if (Objects.equals(fn.getContent_id(), optional.get().getId()) && fn.getDescription().equals(description) && fn.owner_id.equals(user.getId())) {
                    return;
                }
            }
            descriptionRepository.save(new Description(optional.get().getId(), description, user.getId()));
        } else {
            Content daContent = new Content(hash);
            contentRepository.save(daContent);
            descriptionRepository.save(new Description(daContent.getId(), description, user.getId()));
        }
    }

    public void deleteAllDescriptions() {
        descriptionRepository.deleteAll();
    }

    public void deleteAllDescriptions(User user) {
        descriptionRepository.deleteAllByOwnerId(user.getId());
    }

    public void deleteDescriptionById(Long id) {
        descriptionRepository.deleteById(id);
    }

    public void deleteDescriptionsByContentId(Long content_id, User user) {
        descriptionRepository.deleteByContentId(content_id, user.getId());
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

    public void postTag(String hash, String tag, User user) {
        Optional<Content> optional = contentRepository.findByHashEquals(hash);
        if (optional.isPresent()) {
            List<Tag> byTagContains = tagRepository.findByTagContains(tag);
            for (Tag fn : byTagContains) {
                if (Objects.equals(fn.getContent_id(), optional.get().getId()) && fn.getTag().equals(tag) && fn.owner_id.equals(user.getId())) {
                    return;
                }
            }
            tagRepository.save(new Tag(optional.get().getId(), tag, user.getId()));
        } else {
            Content daContent = new Content(hash);
            contentRepository.save(daContent);
            tagRepository.save(new Tag(daContent.getId(), tag, user.getId()));
        }
    }

    public void deleteAllTags() {
        tagRepository.deleteAll();
    }

    public void deleteAllTags(User user) {
        tagRepository.deleteAllByOwnerId(user.getId());
    }

    public void deleteTagById(Long id) {
        tagRepository.deleteById(id);
    }

    public void deleteTagsByContentId(Long content_id, User user) {
        tagRepository.deleteByContentId(content_id, user.getId());
    }


    public List<PeerInfo> findAllPeerInfos(String hash) {
        return peerInfoRepository.findPeersByHash(hash);
    }

    public void postPeer(String hash, String text, User user) {
        Optional<Content> optional = contentRepository.findByHashEquals(hash);
        if (optional.isPresent()) {
            List<PeerInfo> peers = peerInfoRepository.findPeersByHash(optional.get().hash);
            for (PeerInfo peerInfo : peers) {
                if (peerInfo.toString().equals(text) && peerInfo.owner_id.equals(user.getId())) {
                    return;
                }
            }
            PeerInfo p = PeerInfo.fromString(text);
            p.setHash(hash);
            p.owner_id = user.getId();
            peerInfoRepository.save(p);
        } else {
            Content daContent = new Content(hash);
            contentRepository.save(daContent);
            PeerInfo p = PeerInfo.fromString(text);
            p.setHash(hash);
            peerInfoRepository.save(p);
        }
    }

    public void deletePeersByHash(String hash, User user) {
        peerInfoRepository.deletePeersByHash(hash, user.getId());
    }

    public void deletePeersByHost(PeerInfo peer) {
        peerInfoRepository.deleteByHost(peer.ip, peer.port);
    }

    public void deleteAllPeers() {
        peerInfoRepository.deleteAll();
    }

    public void deleteAllPeers(User user) {
        peerInfoRepository.deleteAllByOwnerId(user.getId());
    }

    public boolean authentify(User user) {
        if (userRepository.authentifies(user.getUsername(), user.getPassword_hash())) {
            user.setId(userRepository.findUser(user.getUsername(), user.getPassword_hash()).get().getId());
            return true;
        }
        return false;
    }

    public boolean createUser(User user) {
        if (userRepository.existsByUsernameEquals(user.getUsername())) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public boolean modifyUser(User user, String new_password) {
        if (!userRepository.existsByUsernameEquals(user.getUsername())) {
            return false;
        }
        Optional<User> db_user = userRepository.findUser(user.getUsername(), user.getPassword_hash());
        if (db_user.isPresent()) {
            userRepository.deleteById(db_user.get().getId());
            db_user.get().setPassword_hash(user.getPassword_hash(), HashCalculator.getStringHash(new_password));
            userRepository.save(db_user.get());
        }
        return true;
    }

    public boolean deleteUser(User user) {
        if (!userRepository.existsByUsernameEquals(user.getUsername())) {
            return false;
        }
        Optional<User> db_user = userRepository.findUser(user.getUsername(), user.getPassword_hash());
        db_user.ifPresent(value -> userRepository.deleteById(value.getId()));
        return true;
    }
}
