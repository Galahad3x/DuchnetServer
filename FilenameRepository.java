package duchnet.duchnet.repository;

import duchnet.duchnet.models.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilenameRepository extends JpaRepository<Description, String> {
}
