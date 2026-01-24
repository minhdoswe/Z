package minhdoswe.socialnetwork.z.modules.user.internal.service;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.auth.api.UserDeactivateRequestEvent;
import minhdoswe.socialnetwork.z.modules.user.api.UserDeactivatedEvent;
import minhdoswe.socialnetwork.z.modules.user.internal.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserEventListener {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserRepository userRepository;

    @TransactionalEventListener
    @ApplicationModuleListener
    public void deactivateUser(UserDeactivateRequestEvent event) {
        userRepository.deactivateUser(event.userId());
        applicationEventPublisher.publishEvent(new UserDeactivatedEvent(event.userId()));
    }
}
