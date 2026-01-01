package minhdoswe.socialnetwork.z.dto.response.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {

    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
