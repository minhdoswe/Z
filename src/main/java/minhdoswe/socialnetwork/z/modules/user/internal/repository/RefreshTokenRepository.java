package minhdoswe.socialnetwork.z.modules.user.internal.repository;

import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.RefreshToken;
import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Query("""
        UPDATE RefreshToken rt
        SET rt.isRevoked = true
        WHERE rt.user = :user
""")
    void invalidateByUser(User user);

    @Modifying
    @Query("""
        DELETE RefreshToken rt
        WHERE rt.isRevoked = true
""")
    void deleteByRevoked();
}
