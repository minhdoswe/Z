package minhdoswe.socialnetwork.z.modules.user.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserAuthenticationDTO {

    private Long id;
    private String username;
    private List<String> roles;
    private String email;
    private String password;
    private boolean deleted;
}
