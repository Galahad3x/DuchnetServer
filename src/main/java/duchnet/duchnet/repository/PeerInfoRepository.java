package duchnet.duchnet.repository;

import duchnet.duchnet.models.PeerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeerInfoRepository extends JpaRepository<PeerInfo, Long> {
    @Query("select p from PeerInfo p where upper(p.hash) = upper(?1)")
    List<PeerInfo> findPeersByHash(String hash);

    @Modifying
    @Query("delete from PeerInfo p where upper(p.hash) = upper(?1)")
    void deletePeersByHash(String hash);
}
