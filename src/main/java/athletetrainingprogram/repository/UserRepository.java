package athletetrainingprogram.repository;

import athletetrainingprogram.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("select u from User u where lower(u.username) like lower(:username)")
    List<User> findAllByUsername(@Param("username") String username, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.email = ?1 OR u.username = ?2")
    List<User> findAllByEmailOrUsername(String email, String username);

    @Query("SELECT u FROM User u WHERE u.email = ?1 OR u.username = ?1")
    Optional<User> findByEmailOrUsername(String credential);

}
