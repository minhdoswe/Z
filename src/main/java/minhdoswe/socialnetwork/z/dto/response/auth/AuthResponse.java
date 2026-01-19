package minhdoswe.socialnetwork.z.dto.response.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private Long userId;

    private String accessToken;

    private String refreshToken;
}
