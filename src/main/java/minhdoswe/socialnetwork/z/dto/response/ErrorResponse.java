package minhdoswe.socialnetwork.z.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;
}
