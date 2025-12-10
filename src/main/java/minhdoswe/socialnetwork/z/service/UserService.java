package minhdoswe.socialnetwork.z.service;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.dto.request.auth.DeleteAccountRequest;
import minhdoswe.socialnetwork.z.dto.request.auth.RecoverAccountRequest;
import minhdoswe.socialnetwork.z.entity.User;
import minhdoswe.socialnetwork.z.mapper.AuthMapper;
import minhdoswe.socialnetwork.z.repository.UserRepository;
import minhdoswe.socialnetwork.z.util.SecurityUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final AuthMapper authMapper;

    @Transactional
    public void deactivateAccount(DeleteAccountRequest deleteAccountRequest) {

        User user = securityUtils.getCurrentUser();
        if (!passwordEncoder.matches(deleteAccountRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        userRepository.delete(user);
    }

    @Transactional
    public void recoverAccount(RecoverAccountRequest recoverAccountRequest) {
        User user = userRepository.findByIdentifierIncludingDeleted(recoverAccountRequest.getIdentifier())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        authService.authenticate(authMapper.toLoginRequest(recoverAccountRequest));

        user.setDeleted(false);
        user.setDeletedAt(null);
        userRepository.save(user);
    }
}
