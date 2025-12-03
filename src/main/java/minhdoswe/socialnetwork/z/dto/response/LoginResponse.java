package minhdoswe.socialnetwork.z.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String username;
    private List<String> roles;
}
