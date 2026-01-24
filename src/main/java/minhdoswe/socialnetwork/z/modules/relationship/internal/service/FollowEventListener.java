package minhdoswe.socialnetwork.z.modules.relationship.internal.service;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.relationship.internal.repository.FollowRepository;
import minhdoswe.socialnetwork.z.modules.auth.api.UserDeactivateRequestEvent;
import minhdoswe.socialnetwork.z.modules.user.api.UserDeactivatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class FollowEventListener {

    private final FollowRepository followRepository;

    @TransactionalEventListener
    @ApplicationModuleListener
    public void hideFollow(UserDeactivatedEvent event) {

        followRepository.hideAllByUser(event.userId());
    }
}
