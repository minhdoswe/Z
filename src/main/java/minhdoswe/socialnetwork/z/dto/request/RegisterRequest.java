package minhdoswe.socialnetwork.z.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @Size(min = 2, max = 20, message = "Username must be between 4 and 20 characters")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Size(min = 2, max = 20, message = "Firstname must be between 2 and 20 characters")
    @NotBlank(message = "Firstname cannot be blank")
    private String firstname;

    @Size(min = 2, max = 20, message = "Lastname must be between 2 and 20 characters")
    @NotBlank(message = "Lastname cannot be blank")
    private String lastname;

    @NotBlank(message = "Contact Information cannot be blank")
    @Pattern(
            regexp = "^([\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4})|([0-9\\-\\+]{9,15})$",
            message = "Must be a valid email or phone number"
    )
    private String contactInfo;
}
