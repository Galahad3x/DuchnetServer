package duchnet.duchnet.repository;

import duchnet.duchnet.models.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to access descriptions
 */
@Repository
public interface DescriptionRepository extends JpaRepository<Description, Long> {
    /**
     * Find all the descriptions that contain the text
     *
     * @param description the text to look for inside the description
     * @return A list of descriptions
     */
    @Query("select d from Description d where upper(d.description) like upper(concat('%', ?1, '%'))")
    List<Description> findByDescriptionContains(String description);

    /**
     * Find all the descriptions of a concrete hash
     *
     * @param content_id The id of this hash in the database
     * @return A list of all the descriptions matching the pattern
     */
    @Query("select d from Description d where d.content_id = ?1")
    List<Description> findByContentIdEquals(Long content_id);

    /**
     * Delete all the descriptions of a concrete hash
     *
     * @param content_id The id of this hash in the database
     */
    @Modifying
    @Query("delete from Description d where d.content_id = ?1 and d.owner_id = ?2")
    void deleteByContentId(Long content_id, Long oid);

    /**
     * Delete all descriptions by who owns them
     *
     * @param owner_id Id of the owner
     */
    @Modifying
    @Query("delete from Description f where upper(f.owner_id) = upper(?1)")
    void deleteAllByOwnerId(Long owner_id);
}
