package minhdoswe.socialnetwork.z.modules.user.internal.repository;

import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    @Query("""
        SELECT u FROM User u
        WHERE u.username = :identifier
            OR u.email = :identifier
            OR u.phoneNumber = :identifier
        """)
    Optional<User> findByIdentifier(String identifier);

    @Query(value = """
        SELECT * FROM users
        WHERE username = :identifier
        OR email = :identifier
        OR phone_number = :identifier
        """, nativeQuery = true)
    Optional<User> findByIdentifierIncludingDeleted(@Param("identifier") String identifier);

    @Query(value = "SELECT COUNT(*) FROM users WHERE username = :username", nativeQuery = true)
    long countByUsernameRaw(@Param("username") String username);

    default boolean existsByUsernameIncludingDeleted(String username) {
        return countByUsernameRaw(username) > 0;
    }

    @Query(value = "SELECT COUNT(*) FROM users WHERE email = :email", nativeQuery = true)
    long countByEmailRaw(@Param("email") String email);

    default boolean existsByEmailIncludingDeleted(String email) {
        return countByEmailRaw(email) > 0;
    }

    @Query(value = "SELECT COUNT(*) FROM users WHERE phone_number = :phone_number", nativeQuery = true)
    long countByPhoneNumberRaw(@Param("phone_number") String phoneNumber);

    default boolean existsByPhoneNumberIncludingDeleted(String phoneNumber) {
        return countByPhoneNumberRaw(phoneNumber) > 0;
    }

    @Query(value = """
        SELECT COUNT(*) FROM users
        WHERE username = :identifier
        OR email = :identifier
        OR phone_number = :identifier
        """, nativeQuery = true)
    long countByIdentifierRaw(@Param("identifier") String identifier);

    default boolean existsByIdentifierIncludingDeleted(String identifier) {
        return countByIdentifierRaw(identifier) > 0;
    }

    @Query("""
        SELECT u FROM User u
        WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))
        """)
    List<User> search(@Param("keyword") String keyword);

    @Modifying
    @Query("""
        UPDATE User u
        SET u.deleted = true
        WHERE u.id = :userId
""")
    void deactivateUser(Long userId);

}
