package duchnet.duchnet.repository;

import duchnet.duchnet.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    /**
     * Find tags that contain the text
     * @param tag the tag
     * @return List of Tags
     */
    @Query("select t from Tag t where upper(t.tag) like upper(concat('%', ?1, '%'))")
    List<Tag> findByTagContains(String tag);

    /**
     * Find all the tags of a concrete hash
     * @param content_id The id of this hash in the database
     * @return A list of all the tags matching the pattern
     */
    @Query("select t from Tag t where t.content_id = ?1")
    List<Tag> findByContentIdEquals(Long content_id);

    /**
     * Delete all the tags of a concrete hash
     * @param content_id The id of this hash in the database
     */
    @Modifying
    @Query("delete from Tag t where t.content_id = ?1")
    void deleteByContentId(Long content_id);
}
