package minhdoswe.socialnetwork.z.repository;

import minhdoswe.socialnetwork.z.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    @Query("""
        SELECT u FROM User u
        WHERE u.username = :identifier
            OR u.email = :identifier
            OR u.phoneNumber = :identifier
        """)
    Optional<User> findByIdentifier(@Param("identifier") String identifier);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query("""
        SELECT u FROM User u
        WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))
        """)
    List<User> search(@Param("keyword") String keyword);

}
