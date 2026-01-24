package minhdoswe.socialnetwork.z.modules.auth.internal.security;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.user.api.dto.UserAuthenticationDTO;
import minhdoswe.socialnetwork.z.modules.user.api.UserPort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserPort userPort;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        UserAuthenticationDTO userAuthenticationDTO = userPort.getUserInfo(identifier);
        return new CustomUserDetails(userAuthenticationDTO);
    }
}