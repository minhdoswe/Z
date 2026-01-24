package minhdoswe.socialnetwork.z.modules.auth.internal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.common.annotation.CurrentUserId;
import minhdoswe.socialnetwork.z.modules.auth.internal.model.dto.*;
import minhdoswe.socialnetwork.z.modules.auth.internal.repository.RefreshTokenRepository;
import minhdoswe.socialnetwork.z.modules.auth.internal.service.AuthService;
import minhdoswe.socialnetwork.z.modules.auth.internal.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse authResponse = authService.register(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {

        AuthResponse authResponse = authService.login(loginRequest);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(@CurrentUserId Long currentUserId) {
        authService.logout(currentUserId);
        return ResponseEntity.ok("Log out successfully");
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {

        AuthResponse authResponse = authService.refresh(refreshTokenRequest);

        return ResponseEntity.ok(authResponse);
    }

    @DeleteMapping("/me/deactivation")
    public ResponseEntity<String> deactivateAccount(@CurrentUserId Long currentUserId, @Valid @RequestBody DeactivateAccountRequest deactivateAccountRequest) {

        authService.deactivateAccount(currentUserId, deactivateAccountRequest);
        return ResponseEntity.ok("Account deactivated, schedule to permanently delete after 30 day");
    }

    @PostMapping("/me/reactivation")
    public ResponseEntity<String> recoverAccount(@Valid @RequestBody RecoverAccountRequest recoverAccountRequest) {
        recoverAccount(recoverAccountRequest);
        return ResponseEntity.ok("Account recovered successfully");
    }
}
