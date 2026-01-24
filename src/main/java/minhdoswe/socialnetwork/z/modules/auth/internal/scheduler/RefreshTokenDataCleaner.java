package minhdoswe.socialnetwork.z.modules.auth.internal.scheduler;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.auth.internal.repository.RefreshTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RefreshTokenDataCleaner {

    private final RefreshTokenRepository refreshTokenRepository;
    private final Clock clock;

    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void deleteOldRefreshToken() {
        refreshTokenRepository.deleteUnusedToken(LocalDateTime.now(clock));
    }
}
