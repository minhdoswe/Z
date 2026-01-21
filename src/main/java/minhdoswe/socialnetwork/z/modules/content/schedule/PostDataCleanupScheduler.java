package minhdoswe.socialnetwork.z.modules.content.schedule;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.content.repository.PostRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostDataCleanupScheduler {

    private final Clock clock;
    private final PostRepository postRepository;

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void hardDeleteOldPosts() {
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime cutOff = now.minusDays(30);
        postRepository.deletePermanentlyOlderThan(cutOff);
    }
}
