package minhdoswe.socialnetwork.z.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import minhdoswe.socialnetwork.z.entity.Visibility;

@Data
public class PostRequest {

    @NotNull
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private Visibility visibility;
}
