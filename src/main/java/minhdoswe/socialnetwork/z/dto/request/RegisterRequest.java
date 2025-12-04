package minhdoswe.socialnetwork.z.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import minhdoswe.socialnetwork.z.validation.ExactlyOneContact;
import minhdoswe.socialnetwork.z.validation.StrongPassword;
import minhdoswe.socialnetwork.z.validation.ValidPhoneNumber;
import minhdoswe.socialnetwork.z.validation.ValidUsername;

@Getter
@Setter
@ExactlyOneContact
public class RegisterRequest {

    @ValidUsername
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 2, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @StrongPassword
    @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "Firstname cannot be blank")
    @Size(min = 2, max = 20, message = "Firstname must be between 2 and 20 characters")
    private String firstname;

    @NotBlank(message = "Lastname cannot be blank")
    @Size(min = 2, max = 20, message = "Lastname must be between 2 and 20 characters")
    private String lastname;

    @Email(message = "Invalid email format")
    private String email;

    @ValidPhoneNumber
    private String phoneNumber;
}
