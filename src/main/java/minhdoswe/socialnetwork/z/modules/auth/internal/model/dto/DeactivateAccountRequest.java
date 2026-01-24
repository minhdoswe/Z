package minhdoswe.socialnetwork.z.modules.auth.internal.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeactivateAccountRequest {

    @NotBlank(message = "Password is required for confirmation")
    private String password;
}
