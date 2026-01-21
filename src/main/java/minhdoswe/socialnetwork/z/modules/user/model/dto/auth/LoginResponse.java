package minhdoswe.socialnetwork.z.modules.user.model.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String username;
    private List<String> roles;
}
