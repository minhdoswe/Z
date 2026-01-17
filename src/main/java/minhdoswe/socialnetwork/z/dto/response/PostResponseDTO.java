package minhdoswe.socialnetwork.z.dto.response;

import lombok.Data;
import minhdoswe.socialnetwork.z.dto.response.author.AuthorDTO;

import java.time.LocalDateTime;

@Data
public class PostResponseDTO {

    private AuthorDTO author;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
