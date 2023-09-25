package grauebcf.schoolapppro.repository;

import grauebcf.schoolapppro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = ?1 AND  u.password =?2" )
    boolean isUserValid(String username, String password);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email= ?1")
    boolean emailExists(String  email);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User getUserByEmail(String email);
}
