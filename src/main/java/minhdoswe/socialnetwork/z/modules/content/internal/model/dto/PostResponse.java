package minhdoswe.socialnetwork.z.modules.content.internal.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import minhdoswe.socialnetwork.z.modules.user.dto.UserDTO;

import java.time.LocalDateTime;

@Data
@Builder
@Accessors(chain = true)
public class PostResponse {

    private Long id;
    private UserDTO userDTO;
    private String title;
    private String content;
    private Long upvoteCount;
    private Long downvoteCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
