package minhdoswe.socialnetwork.z.modules.user.internal.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minhdoswe.socialnetwork.z.modules.user.internal.model.dto.auth.RefreshTokenRequest;
import minhdoswe.socialnetwork.z.modules.user.internal.model.dto.auth.AuthResponse;
import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.RefreshToken;
import minhdoswe.socialnetwork.z.modules.user.internal.auth.AccountDeactivatedException;
import minhdoswe.socialnetwork.z.modules.user.internal.auth.RefreshTokenNotFoundException;
import minhdoswe.socialnetwork.z.modules.user.internal.auth.UserAlreadyExistsException;
import minhdoswe.socialnetwork.z.modules.user.internal.mapper.UserMapper;
import minhdoswe.socialnetwork.z.modules.user.internal.repository.RefreshTokenRepository;
import minhdoswe.socialnetwork.z.modules.user.internal.repository.UserRepository;
import minhdoswe.socialnetwork.z.modules.user.internal.model.dto.auth.LoginRequest;
import minhdoswe.socialnetwork.z.modules.user.internal.model.dto.auth.RegisterRequest;
import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.User;
import minhdoswe.socialnetwork.z.common.util.JwtUtils;
import minhdoswe.socialnetwork.z.common.util.SecurityUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final SecurityUtils securityUtils;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsernameIncludingDeleted(request.getUsername())) {
            throw new UserAlreadyExistsException("Username: " + request.getUsername() + " is already taken");
        }
        if (userRepository.existsByEmailIncludingDeleted(request.getEmail())) {
            throw new UserAlreadyExistsException("Email is already linked to another account");
        }
        if (userRepository.existsByPhoneNumberIncludingDeleted(request.getPhoneNumber())) {
            throw new UserAlreadyExistsException("Phone number is already linked to another account");
        }
        User user = userMapper.toUser(request);
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.USER);
        userRepository.save(user);

        return generateRefreshTokenAndAccessToken(user);
    }

    public AuthResponse login(LoginRequest loginRequest) {

        User user = userRepository.findByIdentifierIncludingDeleted(loginRequest.getIdentifier())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        Authentication authentication = this.authenticate(loginRequest);

        if (user.isDeleted()) {
            throw new AccountDeactivatedException("Account is deleted, please recover to continue");
        }
        return generateRefreshTokenAndAccessToken(user);
    }

    @Transactional
    public void logout() {

        User user = securityUtils.getCurrentUser();
        log.info(user.getUsername());
        refreshTokenRepository.invalidateByUser(user);
    }

    public Authentication authenticate(LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getIdentifier(), loginRequest.getPassword())
        );
    }

    public AuthResponse generateRefreshTokenAndAccessToken(User user) {

        String refreshToken = refreshTokenService.generate(user).getToken();
        String accessToken = jwtUtils.generateAccessToken(user.getId(), String.valueOf(user.getRole()));
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .build();
    }

    public AuthResponse refresh(RefreshTokenRequest refreshTokenRequest) {


        RefreshToken rt = refreshTokenRepository.findByToken(refreshTokenRequest.getToken())
                .orElseThrow(() -> new RefreshTokenNotFoundException("refresh token not found"));

        refreshTokenService.validate(rt.getToken());

        refreshTokenService.invalidate(rt);

        return generateRefreshTokenAndAccessToken(rt.getUser());
    }
}
