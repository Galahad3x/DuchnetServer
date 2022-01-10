package duchnet.duchnet.repository;

import duchnet.duchnet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository to access users
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Check if the username exists
     *
     * @param username username
     * @return True if it exists
     */
    boolean existsByUsernameEquals(String username);

    /**
     * Check if the authentication is correct
     *
     * @param username      username
     * @param password_hash sha256 of the password
     * @return True if correct
     */
    @Query("select (count(u) > 0) from duchnetUser u where u.username = ?1 and u.password_hash = ?2")
    boolean authentifies(String username, String password_hash);

    /**
     * Get this concrete user
     *
     * @param username      username
     * @param password_hash sha256 of the password
     * @return User
     */
    @Query("select u from duchnetUser u where u.username = ?1 and u.password_hash = ?2")
    Optional<User> findUser(String username, String password_hash);
}
