package minhdoswe.socialnetwork.z.service;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.dto.request.auth.RefreshTokenRequest;
import minhdoswe.socialnetwork.z.entity.RefreshToken;
import minhdoswe.socialnetwork.z.entity.User;
import minhdoswe.socialnetwork.z.exception.auth.RefreshTokenExpiredException;
import minhdoswe.socialnetwork.z.exception.auth.RefreshTokenNotFoundException;
import minhdoswe.socialnetwork.z.exception.auth.RefreshTokenRevokedException;
import minhdoswe.socialnetwork.z.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final Clock clock;

    @Value("{jwt.refresh-token-expiration}")
    private long REFRESH_TOKEN_EXPIRATION;

    public RefreshToken validate(String token) {

        RefreshToken refreshToken = refreshTokenRepository.findRefreshTokenByToken(token)
                .orElseThrow(() -> new RefreshTokenNotFoundException("refresh token not found"));

        if (refreshToken.isRevoked()) {
            throw new RefreshTokenRevokedException("refresh token revoked");
        }

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now(clock))) {
            throw new RefreshTokenExpiredException("refresh token expired");
        }

        return refreshToken;
    }

    @Transactional
    public void invalidate(RefreshToken refreshToken) {

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public RefreshToken generate(User user) {

        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);

        return refreshTokenRepository.save(
                RefreshToken.builder()
                    .user(user)
                    .isRevoked(false)
                    .expiresAt(LocalDateTime.now(clock).plus(REFRESH_TOKEN_EXPIRATION, ChronoUnit.MILLIS))
                    .token(Base64.getUrlEncoder().withoutPadding().encodeToString(bytes))
                    .build());
    }

    public RefreshToken refresh(String token) {

        RefreshToken refreshToken = validate(token);
        invalidate(refreshToken);
        return generate(refreshToken.getUser());
    }
}
