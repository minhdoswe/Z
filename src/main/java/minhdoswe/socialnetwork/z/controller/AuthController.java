package minhdoswe.socialnetwork.z.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.dto.request.auth.LoginRequest;
import minhdoswe.socialnetwork.z.dto.response.auth.LoginResponse;
import minhdoswe.socialnetwork.z.dto.request.auth.RegisterRequest;
import minhdoswe.socialnetwork.z.dto.response.auth.RegisterResponse;
import minhdoswe.socialnetwork.z.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest, HttpServletRequest request, HttpServletResponse response) {
        authService.register(registerRequest);

        LoginRequest loginRequest = LoginRequest
                .builder()
                .identifier(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .build();
//        authService.login(loginRequest, request, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                RegisterResponse.builder()
                        .username(registerRequest.getUsername())
                        .firstName(registerRequest.getFirstName())
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {

        LoginResponse loginResponse = authService.login(loginRequest, request, response);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        new SecurityContextLogoutHandler()
                .logout(request, response, authentication);
        return ResponseEntity.ok("Log out sucessfully");
    }



    @RequestMapping("/test")
    public String test() {
        return "oke";
    }
}
