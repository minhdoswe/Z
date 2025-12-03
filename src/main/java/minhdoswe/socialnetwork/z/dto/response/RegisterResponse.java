package minhdoswe.socialnetwork.z.dto.response;

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
