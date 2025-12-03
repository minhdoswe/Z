package minhdoswe.socialnetwork.z.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginRequest {


    private String identifier;
    private String password;
}
