package minhdoswe.socialnetwork.z.mapper;

import minhdoswe.socialnetwork.z.dto.request.PostRequest;
import minhdoswe.socialnetwork.z.dto.response.PostResponse;
import minhdoswe.socialnetwork.z.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface PostMapper {

    Post toPost(PostRequest request);

    PostResponse toPostResponse(Post post);

    void updatePostFromRequest(PostRequest postRequest, @MappingTarget Post existingPost);
}
