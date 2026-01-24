package minhdoswe.socialnetwork.z.modules.auth.internal.repository;

import minhdoswe.socialnetwork.z.modules.auth.internal.model.entity.RefreshToken;
import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Query("""
        UPDATE RefreshToken rt
        SET rt.isRevoked = true
        WHERE rt.userId = :userId
""")
    void invalidateByUserId(Long userId);

    @Modifying
    @Query("""
        DELETE RefreshToken rt
        WHERE rt.isRevoked = true
        OR rt.expiresAt < :time
""")
    void deleteUnusedToken(LocalDateTime time);
}
