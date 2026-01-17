package minhdoswe.socialnetwork.z.mapper;

import minhdoswe.socialnetwork.z.dto.request.VoteRequest;
import minhdoswe.socialnetwork.z.dto.response.VoteDTO;
import minhdoswe.socialnetwork.z.dto.response.VoteResponse;
import minhdoswe.socialnetwork.z.entity.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface VoteMapper {

    VoteResponse toVoteResponse(VoteRequest voteRequest);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "post.id", target = "postId")
    VoteDTO toVoteDTO(Vote vote);
}
