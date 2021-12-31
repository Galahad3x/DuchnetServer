package duchnet.duchnet.repository;

import duchnet.duchnet.models.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescriptionRepository extends JpaRepository<Description, Long> {
    List<Description> findByContent_idEquals(Long content_id);

}
