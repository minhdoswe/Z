package minhdoswe.socialnetwork.z.modules.user.api.dto;

import lombok.Data;

@Data
public class CreateUserResponse {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
}
