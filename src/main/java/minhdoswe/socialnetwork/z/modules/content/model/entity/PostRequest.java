package minhdoswe.socialnetwork.z.modules.content.model.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import minhdoswe.socialnetwork.z.modules.content.enums.Visibility;

@Data
public class PostRequest {

    @NotNull
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Visibility visibility;
}
