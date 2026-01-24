package minhdoswe.socialnetwork.z.modules.auth.internal.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RecoverAccountRequest {

    @NotBlank(message = "identifier is required")
    private String identifier;
    @NotBlank(message = "password is required")
    private String password;
}
