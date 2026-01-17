package minhdoswe.socialnetwork.z.dto.response;

import lombok.Data;
import minhdoswe.socialnetwork.z.enums.VoteStatus;

@Data
public class VoteDTO {

    private Long postId;
    private Long userId;
    private VoteStatus voteStatus;
}
