package duchnet.duchnet.repository;

import duchnet.duchnet.models.PeerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to access PeerInfos
 */
@Repository
public interface PeerInfoRepository extends JpaRepository<PeerInfo, Long> {
    /**
     * Find peers by hash
     *
     * @param hash hash
     * @return peers
     */
    @Query("select p from PeerInfo p where upper(p.hash) = upper(?1)")
    List<PeerInfo> findPeersByHash(String hash);

    /**
     * Delete peers by hash
     *
     * @param hash hash
     * @param oid  owner id
     */
    @Modifying
    @Query("delete from PeerInfo p where upper(p.hash) = upper(?1) and p.owner_id = ?2")
    void deletePeersByHash(String hash, Long oid);

    /**
     * Delete peers by host
     *
     * @param ip   ip of host
     * @param port port of host
     */
    @Modifying
    @Query("delete from PeerInfo p where p.ip = ?1 and p.port = ?2")
    void deleteByHost(String ip, Integer port);

    /**
     * delete by owner id
     *
     * @param owner_id owner
     */
    @Modifying
    @Query("delete from PeerInfo p where p.owner_id = ?1")
    void deleteAllByOwnerId(Long owner_id);
}
