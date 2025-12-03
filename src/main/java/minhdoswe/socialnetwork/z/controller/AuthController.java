package minhdoswe.socialnetwork.z.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.dto.request.LoginRequest;
import minhdoswe.socialnetwork.z.dto.response.LoginResponse;
import minhdoswe.socialnetwork.z.dto.request.RegisterRequest;
import minhdoswe.socialnetwork.z.dto.response.RegisterResponse;
import minhdoswe.socialnetwork.z.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        System.out.println("here");

        authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                RegisterResponse.builder()
                        .username(request.getUsername())
                        .firstName(request.getFirstname())
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        //Create token
        Authentication authentication = authService.authenticate(loginRequest);

        //Create new empty context
        SecurityContext securityContext =
                SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        //Save to repository (persist)
        securityContextRepository.saveContext(securityContext, request, response);

        //get userdetail from authentication object, which is Principal
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        LoginResponse loginResponse = new LoginResponse(
                userDetails.getUsername(),
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );

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
