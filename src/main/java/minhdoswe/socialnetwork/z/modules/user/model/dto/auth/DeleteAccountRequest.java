package minhdoswe.socialnetwork.z.modules.user.model.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteAccountRequest {

    @NotBlank(message = "Password is required for confirmation")
    private String password;
}
