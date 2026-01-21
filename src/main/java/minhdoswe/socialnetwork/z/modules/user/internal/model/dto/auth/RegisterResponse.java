package minhdoswe.socialnetwork.z.modules.user.internal.model.dto.auth;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

    private String username;
    private String firstName;
}
