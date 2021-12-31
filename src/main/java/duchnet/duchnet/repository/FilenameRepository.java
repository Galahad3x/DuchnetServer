package duchnet.duchnet.repository;

import duchnet.duchnet.models.Description;
import duchnet.duchnet.models.FileName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilenameRepository extends JpaRepository<FileName, Long> {
    @Query("select f from FileName f where f.content_id = ?1")
    List<FileName> findByContentIdEquals(Long content_id);

}
