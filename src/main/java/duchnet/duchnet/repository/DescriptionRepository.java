package duchnet.duchnet.repository;

import duchnet.duchnet.models.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescriptionRepository extends JpaRepository<Description, Long> {
    @Query("select d from Description d where d.content_id = ?1")
    List<Description> findByContentIdEquals(Long content_id);

    @Modifying
    @Query("delete from Description d where d.content_id = ?1")
    int deleteByContentId(Long content_id);


}
