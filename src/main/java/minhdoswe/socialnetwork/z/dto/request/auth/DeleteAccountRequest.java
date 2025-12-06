package minhdoswe.socialnetwork.z.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteAccountRequest {

    @NotBlank(message = "Password is required for confirmation")
    private String password;
}
