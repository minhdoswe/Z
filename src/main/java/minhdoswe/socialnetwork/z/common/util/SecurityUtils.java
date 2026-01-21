package minhdoswe.socialnetwork.z.common.util;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.user.model.entity.User;
import minhdoswe.socialnetwork.z.modules.user.exception.auth.UserNotAuthenticatedException;
import minhdoswe.socialnetwork.z.common.security.user.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) authentication.getPrincipal()).getUser();
        }

        throw new UserNotAuthenticatedException("User not authenticated");
    }
}
