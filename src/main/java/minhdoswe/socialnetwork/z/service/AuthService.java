package minhdoswe.socialnetwork.z.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minhdoswe.socialnetwork.z.dto.request.auth.RefreshTokenRequest;
import minhdoswe.socialnetwork.z.dto.response.auth.AuthResponse;
import minhdoswe.socialnetwork.z.dto.response.auth.LoginResponse;
import minhdoswe.socialnetwork.z.dto.response.auth.RefreshTokenResponse;
import minhdoswe.socialnetwork.z.entity.RefreshToken;
import minhdoswe.socialnetwork.z.exception.auth.AccountDeactivatedException;
import minhdoswe.socialnetwork.z.exception.auth.RefreshTokenNotFoundException;
import minhdoswe.socialnetwork.z.exception.auth.UserAlreadyExistsException;
import minhdoswe.socialnetwork.z.mapper.UserMapper;
import minhdoswe.socialnetwork.z.repository.RefreshTokenRepository;
import minhdoswe.socialnetwork.z.repository.UserRepository;
import minhdoswe.socialnetwork.z.dto.request.auth.LoginRequest;
import minhdoswe.socialnetwork.z.dto.request.auth.RegisterRequest;
import minhdoswe.socialnetwork.z.entity.User;
import minhdoswe.socialnetwork.z.util.JwtUtils;
import minhdoswe.socialnetwork.z.util.SecurityUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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
        refreshTokenRepository.invalidateTokensByUser(user);
    }

    public Authentication authenticate(LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getIdentifier(), loginRequest.getPassword())
        );
    }

    public AuthResponse generateRefreshTokenAndAccessToken(User user) {

        String refreshToken = refreshTokenService.generate(user).getToken();
        String accessToken = jwtUtils.generateAccessToken(user.getUsername());
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthResponse refresh(RefreshTokenRequest refreshTokenRequest) {


        RefreshToken rt = refreshTokenRepository.findRefreshTokenByToken(refreshTokenRequest.getToken())
                .orElseThrow(() -> new RefreshTokenNotFoundException("refresh token not found"));

        refreshTokenService.validate(rt.getToken());

        refreshTokenService.invalidate(rt);

        return generateRefreshTokenAndAccessToken(rt.getUser());
    }
}
