package minhdoswe.socialnetwork.z.modules.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.user.model.dto.auth.LoginRequest;
import minhdoswe.socialnetwork.z.modules.user.model.dto.auth.RefreshTokenRequest;
import minhdoswe.socialnetwork.z.modules.user.model.dto.auth.AuthResponse;
import minhdoswe.socialnetwork.z.modules.user.model.dto.auth.RegisterRequest;
import minhdoswe.socialnetwork.z.modules.user.repository.RefreshTokenRepository;
import minhdoswe.socialnetwork.z.modules.user.service.AuthService;
import minhdoswe.socialnetwork.z.modules.user.service.RefreshTokenService;
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
    public ResponseEntity<?> logout() {
        authService.logout();
        return ResponseEntity.ok("Log out successfully");
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {

        AuthResponse authResponse = authService.refresh(refreshTokenRequest);

        return ResponseEntity.ok(authResponse);
    }
}
