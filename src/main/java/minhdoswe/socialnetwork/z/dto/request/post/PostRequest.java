package minhdoswe.socialnetwork.z.dto.request.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import minhdoswe.socialnetwork.z.enums.Visibility;

@Data
public class PostRequest {

    @NotNull
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Visibility visibility;
}
