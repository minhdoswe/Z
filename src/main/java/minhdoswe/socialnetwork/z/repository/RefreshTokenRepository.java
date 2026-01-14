package minhdoswe.socialnetwork.z.repository;

import minhdoswe.socialnetwork.z.entity.RefreshToken;
import minhdoswe.socialnetwork.z.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Ref;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findRefreshTokenByToken(String token);

    @Modifying
    @Query("""
        UPDATE RefreshToken rt
        SET rt.isRevoked = true
        WHERE rt.user = :user
""")
    void invalidateTokensByUser(@Param("user") User user);
}
