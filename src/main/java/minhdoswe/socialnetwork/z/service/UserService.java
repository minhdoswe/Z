package minhdoswe.socialnetwork.z.service;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.dto.request.auth.DeleteAccountRequest;
import minhdoswe.socialnetwork.z.entity.User;
import minhdoswe.socialnetwork.z.repository.UserRepository;
import minhdoswe.socialnetwork.z.util.SecurityUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    private final PasswordEncoder passwordEncoder;

    public void deactivateAccount(DeleteAccountRequest deleteAccountRequest) {

        User user = securityUtils.getCurrentUser();
        if (!passwordEncoder.matches(deleteAccountRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        userRepository.delete(user);
    }
}
