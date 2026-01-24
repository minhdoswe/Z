package minhdoswe.socialnetwork.z.modules.user.internal.controller;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.auth.internal.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final AuthService authService;


}
