package minhdoswe.socialnetwork.z.modules.user.internal.model.dto.auth;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import minhdoswe.socialnetwork.z.modules.user.internal.validation.*;

@Getter
@Setter
@ContactMethodSelection
public class RegisterRequest {

    @Username
    private String username;

    @PasswordComplexity
    private String password;

    @Name
    private String firstName;

    @Name
    private String lastName;

    @Email
    private String email;

    @PhoneNumber
    private String phoneNumber;
}
