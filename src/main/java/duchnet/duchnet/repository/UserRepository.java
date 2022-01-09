package duchnet.duchnet.repository;

import duchnet.duchnet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsernameEquals(String username);

    @Query("select (count(u) > 0) from User u where u.username = ?1 and u.password_hash = ?2")
    boolean authentifies(String username, String password_hash);

    @Query("select u from User u where u.username = ?1 and u.password_hash = ?2")
    Optional<User> findUser(String username, String password_hash);


}
