package minhdoswe.socialnetwork.z.modules.user.scheduler;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.content.repository.PostRepository;
import minhdoswe.socialnetwork.z.modules.user.repository.RefreshTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@RequiredArgsConstructor
@Component
public class UserDataCleanupScheduler {

    private final PostRepository postRepository;
    private final Clock clock;
    private final RefreshTokenRepository refreshTokenRepository;

    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void deleteOldRefreshToken() {
        refreshTokenRepository.deleteByRevoked();
    }

}
