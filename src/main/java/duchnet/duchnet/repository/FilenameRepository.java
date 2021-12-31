package duchnet.duchnet.repository;

import duchnet.duchnet.models.Description;
import duchnet.duchnet.models.FileName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilenameRepository extends JpaRepository<FileName, Long> {
    List<FileName> findByContent_idEquals(Long content_id);
}
