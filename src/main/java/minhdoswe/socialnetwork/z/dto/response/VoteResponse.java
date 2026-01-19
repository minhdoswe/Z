package minhdoswe.socialnetwork.z.dto.response;

import lombok.Data;
import minhdoswe.socialnetwork.z.enums.VoteStatus;

@Data
public class VoteResponse {

    private Long postId;
    private Long voteScore;
    private VoteStatus voteStatus;
}
