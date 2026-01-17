package minhdoswe.socialnetwork.z.dto.response;

import lombok.Data;
import minhdoswe.socialnetwork.z.enums.VoteStatus;

@Data
public class VoteResponse {

    private Long postId;
    private Long upvoteCount;
    private Long downvoteCount;
    private VoteStatus voteStatus;
}
