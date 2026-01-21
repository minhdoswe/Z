package minhdoswe.socialnetwork.z.modules.engagement.model.dto;

import lombok.Builder;
import lombok.Data;
import minhdoswe.socialnetwork.z.modules.engagement.enums.CommentVoteType;

@Data
@Builder
public class CommentVoteResponse {

    private Long commentId;
    private Long voteScore;
    private CommentVoteType voteVoteType;

}
