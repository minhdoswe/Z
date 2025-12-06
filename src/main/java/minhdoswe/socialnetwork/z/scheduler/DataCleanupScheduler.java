package minhdoswe.socialnetwork.z.scheduler;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.repository.PostRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class DataCleanupScheduler {

    private final PostRepository postRepository;
    private final Clock clock;

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void hardDeleteOldPosts() {
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime cutOff = now.minusDays(30);
        postRepository.deletePermanentlyOlderThan(cutOff);
    }

}
