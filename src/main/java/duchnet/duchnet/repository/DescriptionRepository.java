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
     * Find all the descriptions of a concrete hash
     * @param content_id The id of this hash in the database
     * @return A list of all the descriptions matching the pattern
     */
    @Query("select d from Description d where d.content_id = ?1")
    List<Description> findByContentIdEquals(Long content_id);

    /**
     * Delete all the descriptions of a concrete hash
     * @param content_id The id of this hash in the database
     */
    @Modifying
    @Query("delete from Description d where d.content_id = ?1")
    void deleteByContentId(Long content_id);
}
