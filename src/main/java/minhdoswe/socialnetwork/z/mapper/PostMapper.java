package minhdoswe.socialnetwork.z.mapper;

import minhdoswe.socialnetwork.z.dto.request.post.PostRequest;
import minhdoswe.socialnetwork.z.dto.response.PostResponseDTO;
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

    @Mapping(source = "user", target = "author")
    PostResponseDTO toPostResponse(Post post);

    void updatePostFromRequest(PostRequest postRequest, @MappingTarget Post existingPost);
}
