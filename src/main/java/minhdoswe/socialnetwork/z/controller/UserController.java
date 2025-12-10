package minhdoswe.socialnetwork.z.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.dto.request.auth.DeleteAccountRequest;
import minhdoswe.socialnetwork.z.dto.request.auth.RecoverAccountRequest;
import minhdoswe.socialnetwork.z.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @DeleteMapping("/me")
    public ResponseEntity<String> deactivateAccount(@Valid @RequestBody DeleteAccountRequest deleteAccountRequest) {

        userService.deactivateAccount(deleteAccountRequest);
        return ResponseEntity.ok("Account deactivated, schedule to permanently delete after 30 day");
    }

    @PostMapping("/recover")
    public ResponseEntity<String> recoverAccount(@Valid @RequestBody RecoverAccountRequest recoverAccountRequest) {
        userService.recoverAccount(recoverAccountRequest);
        return ResponseEntity.ok("Account recovered successfully");
    }
}
