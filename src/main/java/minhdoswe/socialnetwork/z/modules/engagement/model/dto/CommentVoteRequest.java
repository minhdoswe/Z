package minhdoswe.socialnetwork.z.modules.engagement.model.dto;

import lombok.Data;
import minhdoswe.socialnetwork.z.modules.engagement.enums.CommentVoteType;

@Data
public class CommentVoteRequest {

    private CommentVoteType commentVoteType;
}
