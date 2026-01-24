package minhdoswe.socialnetwork.z.modules.auth.internal.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minhdoswe.socialnetwork.z.common.annotation.CurrentUserId;
import minhdoswe.socialnetwork.z.modules.auth.internal.mapper.AuthMapper;
import minhdoswe.socialnetwork.z.modules.auth.internal.model.dto.RefreshTokenRequest;
import minhdoswe.socialnetwork.z.modules.auth.internal.model.dto.AuthResponse;
import minhdoswe.socialnetwork.z.modules.auth.internal.model.entity.RefreshToken;
import minhdoswe.socialnetwork.z.modules.user.api.dto.CreateUserRequest;
import minhdoswe.socialnetwork.z.modules.user.api.dto.CreateUserResponse;
import minhdoswe.socialnetwork.z.modules.user.api.dto.UserAuthenticationDTO;
import minhdoswe.socialnetwork.z.modules.auth.api.UserDeactivateRequestEvent;
import minhdoswe.socialnetwork.z.modules.user.api.UserPort;
import minhdoswe.socialnetwork.z.modules.auth.internal.exception.AccountDeactivatedException;
import minhdoswe.socialnetwork.z.modules.auth.internal.exception.RefreshTokenNotFoundException;
import minhdoswe.socialnetwork.z.modules.auth.internal.repository.RefreshTokenRepository;
import minhdoswe.socialnetwork.z.modules.auth.internal.model.dto.LoginRequest;
import minhdoswe.socialnetwork.z.modules.auth.internal.model.dto.RegisterRequest;
import minhdoswe.socialnetwork.z.modules.auth.internal.util.JwtUtils;
import minhdoswe.socialnetwork.z.modules.auth.internal.model.dto.DeactivateAccountRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserPort userPort;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher event;

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        userPort.existByIdentifierIncludedDeleted(request.getUsername(), request.getEmail(), request.getPassword());
        CreateUserRequest createUserRequest = authMapper.toCreateUserRequest(request);
        createUserRequest.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        createUserRequest.setRoles(List.of("USER"));

        CreateUserResponse createUserResponse = userPort.saveUser(createUserRequest);

        return generateRefreshTokenAndAccessToken(createUserResponse.getId());
    }

    public AuthResponse login(LoginRequest loginRequest) {

        UserAuthenticationDTO userDTO = userPort.findByIdentifierIncludedDeleted(loginRequest.getIdentifier());

        Authentication authentication = this.authenticate(loginRequest);

        if (userDTO.isDeleted()) {
            throw new AccountDeactivatedException("Account is deleted, please recover to continue");
        }
        return generateRefreshTokenAndAccessToken(userDTO.getId());
    }

    @Transactional
    public void logout(Long currentUserId) {
        refreshTokenRepository.invalidateByUserId(currentUserId);
    }

    public Authentication authenticate(LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getIdentifier(), loginRequest.getPassword())
        );
    }

    public AuthResponse generateRefreshTokenAndAccessToken(Long userId) {

        UserAuthenticationDTO userDTO = userPort.getUserInfo(userId);

        String refreshToken = refreshTokenService.generate(userId).getToken();
        String accessToken = jwtUtils.generateAccessToken(userId, userDTO.getRoles(), userDTO.getEmail(), userDTO.getUsername());
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(userId)
                .build();
    }

    public AuthResponse refresh(RefreshTokenRequest refreshTokenRequest) {


        RefreshToken rt = refreshTokenRepository.findByToken(refreshTokenRequest.getToken())
                .orElseThrow(() -> new RefreshTokenNotFoundException("refresh token not found"));

        refreshTokenService.validate(rt.getToken());

        refreshTokenService.invalidate(rt);

        return generateRefreshTokenAndAccessToken(rt.getUserId());
    }

    @Transactional
    public void deactivateAccount(@CurrentUserId Long currentUserId, DeactivateAccountRequest deactivateAccountRequest) {

        String password = userPort.getPassword(currentUserId);

        if (!passwordEncoder.matches(deactivateAccountRequest.getPassword(), password)) {
            throw new BadCredentialsException("Incorrect password");
        }

        event.publishEvent(new UserDeactivateRequestEvent(currentUserId));
    }

//    @Transactional
//    public void reactivateAccount(@CurrentUserId Long currentUserId, RecoverAccountRequest recoverAccountRequest) {
//
//        authenticate(authMapper.toLoginRequest(recoverAccountRequest));
//
//        event.publishEvent(new UserReactivateRequestEvent(currentUserId));
//    }
}
