package minhdoswe.socialnetwork.z.modules.engagement.model.dto;

import lombok.Data;
import minhdoswe.socialnetwork.z.modules.engagement.enums.PostVoteType;

@Data
public class PostVoteResponse {

    private Long postId;
    private Long voteScore;
    private PostVoteType postVoteType;
}
