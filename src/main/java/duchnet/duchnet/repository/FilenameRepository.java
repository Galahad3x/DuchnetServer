package duchnet.duchnet.repository;

import duchnet.duchnet.models.FileName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilenameRepository extends JpaRepository<FileName, Long> {
    /**
     * Find all the filenames that contain the text
     *
     * @param filename The filename
     * @return List of FileNames
     */
    @Query("select f from FileName f where upper(f.filename) like upper(concat('%', ?1, '%'))")
    List<FileName> findByFilenameContains(String filename);

    /**
     * Find all the filenames of a concrete hash
     *
     * @param content_id The id of this hash in the database
     * @return A list of all the filenames matching the pattern
     */
    @Query("select f from FileName f where f.content_id = ?1")
    List<FileName> findByContentIdEquals(Long content_id);

    /**
     * Delete all the filenames of a concrete hash
     *
     * @param content_id The id of this hash in the database
     */
    @Modifying
    @Query("delete from FileName f where f.content_id = ?1 and f.owner_id = ?2")
    void deleteByContentId(Long content_id, Long oid);

    /**
     * Delete all filenames by who owns them
     *
     * @param owner_id Id of the owner
     */
    @Modifying
    @Query("delete from FileName f where upper(f.owner_id) = upper(?1)")
    void deleteAllByOwnerId(Long owner_id);
}
