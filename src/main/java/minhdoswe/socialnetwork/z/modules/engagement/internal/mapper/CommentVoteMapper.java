package minhdoswe.socialnetwork.z.modules.engagement.internal.mapper;

import minhdoswe.socialnetwork.z.modules.engagement.internal.model.dto.CommentVoteRequest;
import minhdoswe.socialnetwork.z.modules.engagement.internal.model.dto.CommentVoteResponse;
import minhdoswe.socialnetwork.z.modules.engagement.internal.model.entity.CommentVote;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface CommentVoteMapper {

    CommentVoteResponse toCommentVoteResponse(CommentVote commentVote);

    CommentVoteResponse toCommentVoteResponse(CommentVoteRequest commentVoteRequest);
}
