package minhdoswe.socialnetwork.z.modules.user.internal.model.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private Long userId;

    private String accessToken;

    private String refreshToken;
}
