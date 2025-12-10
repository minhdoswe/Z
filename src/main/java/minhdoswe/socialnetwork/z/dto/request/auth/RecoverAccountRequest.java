package minhdoswe.socialnetwork.z.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RecoverAccountRequest {

    @NotBlank(message = "identifier is required")
    private String identifier;
    @NotBlank(message = "password is required")
    private String password;
}
