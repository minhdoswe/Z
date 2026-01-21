package minhdoswe.socialnetwork.z.modules.engagement.internal.model.dto;

import lombok.Data;
import minhdoswe.socialnetwork.z.modules.engagement.internal.enums.PostVoteType;

@Data
public class VoteDTO {

    private Long postId;
    private Long userId;
    private PostVoteType postVoteType;
}
