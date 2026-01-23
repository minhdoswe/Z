package minhdoswe.socialnetwork.z.modules.relationship.internal.service;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.relationship.internal.repository.FollowRepository;
import minhdoswe.socialnetwork.z.modules.user.api.UserDeactivateAccountEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FollowEvenListener {

    private final FollowRepository followRepository;

    @Transactional
    @ApplicationModuleListener
    public void hideFollow(UserDeactivateAccountEvent event) {

        followRepository.hideAllByUser(event.userId());
    }
}
