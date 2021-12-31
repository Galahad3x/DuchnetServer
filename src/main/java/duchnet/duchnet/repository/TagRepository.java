package duchnet.duchnet.repository;

import duchnet.duchnet.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByContent_idEquals(Long content_id);

}
