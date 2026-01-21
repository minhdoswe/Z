package minhdoswe.socialnetwork.z.modules.engagement.internal.model.dto;

import lombok.Data;
import minhdoswe.socialnetwork.z.modules.engagement.internal.enums.PostVoteType;

@Data
public class PostVoteResponse {

    private Long postId;
    private Long voteScore;
    private PostVoteType postVoteType;
}
