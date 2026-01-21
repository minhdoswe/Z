package minhdoswe.socialnetwork.z.modules.content.model.entity;

import lombok.Data;
import minhdoswe.socialnetwork.z.modules.user.model.dto.UserDTO;

import java.time.LocalDateTime;

@Data
public class PostResponse {

    private Long id;
    private UserDTO author;
    private String title;
    private String content;
    private Long upvoteCount;
    private Long downvoteCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
