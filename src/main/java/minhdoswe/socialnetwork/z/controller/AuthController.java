package minhdoswe.socialnetwork.z.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.dto.request.auth.LoginRequest;
import minhdoswe.socialnetwork.z.dto.request.auth.RefreshTokenRequest;
import minhdoswe.socialnetwork.z.dto.response.auth.AuthResponse;
import minhdoswe.socialnetwork.z.dto.response.auth.LoginResponse;
import minhdoswe.socialnetwork.z.dto.request.auth.RegisterRequest;
import minhdoswe.socialnetwork.z.dto.response.auth.RegisterResponse;
import minhdoswe.socialnetwork.z.entity.RefreshToken;
import minhdoswe.socialnetwork.z.entity.User;
import minhdoswe.socialnetwork.z.exception.auth.RefreshTokenNotFoundException;
import minhdoswe.socialnetwork.z.repository.RefreshTokenRepository;
import minhdoswe.socialnetwork.z.service.AuthService;
import minhdoswe.socialnetwork.z.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse authResponse = authService.register(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {

        AuthResponse authResponse = authService.login(loginRequest);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        authService.logout();
        return ResponseEntity.ok("Log out successfully");
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {

        AuthResponse authResponse = authService.refresh(refreshTokenRequest);

        return ResponseEntity.ok(authResponse);
    }
}
