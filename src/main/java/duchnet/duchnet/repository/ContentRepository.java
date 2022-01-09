package duchnet.duchnet.repository;

import duchnet.duchnet.models.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository to access contents
 */
@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    Optional<Content> findByHashEquals(String hash);
}
