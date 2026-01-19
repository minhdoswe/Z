package minhdoswe.socialnetwork.z.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {

    private Long id;
    private String content;
    private UserDTO userDTO;
    private LocalDateTime createdAt;
}
