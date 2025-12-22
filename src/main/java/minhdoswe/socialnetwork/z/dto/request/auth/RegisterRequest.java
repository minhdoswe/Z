package minhdoswe.socialnetwork.z.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import minhdoswe.socialnetwork.z.validation.*;

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
