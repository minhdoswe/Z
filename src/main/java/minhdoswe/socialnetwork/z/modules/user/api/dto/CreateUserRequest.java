package minhdoswe.socialnetwork.z.modules.user.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateUserRequest {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<String> roles;
}
