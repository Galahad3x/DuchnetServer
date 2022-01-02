package duchnet.duchnet.repository;

import duchnet.duchnet.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("select t from Tag t where t.content_id = ?1")
    List<Tag> findByContentIdEquals(Long content_id);

    @Modifying
    @Query("delete from Tag t where t.content_id = ?1")
    int deleteByContentId(Long content_id);
}
