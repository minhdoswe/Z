package minhdoswe.socialnetwork.z.modules.engagement.internal.mapper;

import minhdoswe.socialnetwork.z.modules.engagement.internal.model.dto.PostVoteRequest;
import minhdoswe.socialnetwork.z.modules.engagement.internal.model.dto.PostVoteResponse;
import minhdoswe.socialnetwork.z.modules.engagement.internal.model.dto.VoteDTO;
import minhdoswe.socialnetwork.z.modules.engagement.internal.model.entity.PostVote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface PostVoteMapper {

    PostVoteResponse toPostVoteResponse(PostVoteRequest postVoteRequest);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "post.id", target = "postId")
    VoteDTO toVoteDTO(PostVote postVote);
}
