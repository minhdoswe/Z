package minhdoswe.socialnetwork.z.dto.request;

import lombok.Data;
import minhdoswe.socialnetwork.z.enums.VoteStatus;

@Data
public class CommentVoteRequest {

    private VoteStatus voteStatus;
}
