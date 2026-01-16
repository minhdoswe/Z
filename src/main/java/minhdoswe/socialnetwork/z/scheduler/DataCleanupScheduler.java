package minhdoswe.socialnetwork.z.scheduler;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.repository.PostRepository;
import minhdoswe.socialnetwork.z.repository.RefreshTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class DataCleanupScheduler {

    private final PostRepository postRepository;
    private final Clock clock;
    private final RefreshTokenRepository refreshTokenRepository;

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void hardDeleteOldPosts() {
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime cutOff = now.minusDays(30);
        postRepository.deletePermanentlyOlderThan(cutOff);
    }

    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void deleteOldRefreshToken() {
        refreshTokenRepository.deleteRefreshTokensByRevoked();
    }

}
