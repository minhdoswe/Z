package minhdoswe.socialnetwork.z.modules.content.internal.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import minhdoswe.socialnetwork.z.modules.user.dto.UserDTO;

import java.time.LocalDateTime;

@Data
@Builder
@Accessors(chain = true)
public class CommentResponse {

    private Long id;
    private String content;
    private UserDTO userDTO;
    private LocalDateTime createdAt;
}
