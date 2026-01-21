package minhdoswe.socialnetwork.z.modules.user.service;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.user.model.dto.auth.DeleteAccountRequest;
import minhdoswe.socialnetwork.z.modules.user.model.dto.auth.RecoverAccountRequest;
import minhdoswe.socialnetwork.z.modules.user.model.entity.User;
import minhdoswe.socialnetwork.z.modules.user.exception.auth.user.UserNotFoundException;
import minhdoswe.socialnetwork.z.modules.user.mapper.AuthMapper;
import minhdoswe.socialnetwork.z.modules.user.repository.UserRepository;
import minhdoswe.socialnetwork.z.common.util.SecurityUtils;
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

    public User getUserById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
    }

    public User getUserByUsername(String username) {

        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username " + username));
    }
}
