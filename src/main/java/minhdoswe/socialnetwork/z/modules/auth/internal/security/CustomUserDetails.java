package minhdoswe.socialnetwork.z.modules.auth.internal.security;

import minhdoswe.socialnetwork.z.modules.user.api.dto.UserAuthenticationDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public record CustomUserDetails(UserAuthenticationDTO userAuthenticationDTO) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAuthenticationDTO.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .toList();
    }

    @Override
    public String getPassword() {
        return userAuthenticationDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userAuthenticationDTO.getUsername();
    }
}
