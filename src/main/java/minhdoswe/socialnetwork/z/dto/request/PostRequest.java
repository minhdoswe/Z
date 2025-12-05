package minhdoswe.socialnetwork.z.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostRequest {

    @NotNull
    String title;

    @NotBlank
    String content;
}
