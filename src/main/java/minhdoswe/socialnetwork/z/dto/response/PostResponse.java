package minhdoswe.socialnetwork.z.dto.response;

import lombok.Data;
import minhdoswe.socialnetwork.z.dto.response.author.AuthorDTO;

import java.time.LocalDateTime;

@Data
public class PostResponse {

    private AuthorDTO author;
    private String title;
    private String content;
    private Long upvoteCount;
    private Long downvoteCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
