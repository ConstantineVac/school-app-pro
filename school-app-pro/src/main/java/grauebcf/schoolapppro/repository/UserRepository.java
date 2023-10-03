package grauebcf.schoolapppro.repository;

import grauebcf.schoolapppro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    boolean isUserValid(@Param("email") String email, @Param("password") String password);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email= ?1")
    boolean emailExists(String  email);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User getUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);
}
