package minhdoswe.socialnetwork.z.repository;

import minhdoswe.socialnetwork.z.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Ref;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findRefreshTokenByToken(String token);
}
