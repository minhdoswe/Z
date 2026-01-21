package minhdoswe.socialnetwork.z.modules.content.model.dto;

import lombok.Data;
import minhdoswe.socialnetwork.z.modules.user.model.dto.UserDTO;

import java.time.LocalDateTime;

@Data
public class CommentResponse {

    private Long id;
    private String content;
    private UserDTO userDTO;
    private LocalDateTime createdAt;
}
