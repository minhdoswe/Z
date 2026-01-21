package minhdoswe.socialnetwork.z.modules.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.user.model.dto.auth.DeleteAccountRequest;
import minhdoswe.socialnetwork.z.modules.user.model.dto.auth.RecoverAccountRequest;
import minhdoswe.socialnetwork.z.modules.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @DeleteMapping("/me/deactivation")
    public ResponseEntity<String> deactivateAccount(@Valid @RequestBody DeleteAccountRequest deleteAccountRequest) {

        userService.deactivateAccount(deleteAccountRequest);
        return ResponseEntity.ok("Account deactivated, schedule to permanently delete after 30 day");
    }

    @PostMapping("/me/reactivation")
    public ResponseEntity<String> recoverAccount(@Valid @RequestBody RecoverAccountRequest recoverAccountRequest) {
        userService.recoverAccount(recoverAccountRequest);
        return ResponseEntity.ok("Account recovered successfully");
    }
}
