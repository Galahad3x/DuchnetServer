package duchnet.duchnet.repository;

import duchnet.duchnet.models.Content;
import duchnet.duchnet.models.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    Optional<Content> findByHashEquals(String hash);
}
