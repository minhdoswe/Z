package minhdoswe.socialnetwork.z.modules.content.mapper;

import minhdoswe.socialnetwork.z.modules.content.model.entity.PostRequest;
import minhdoswe.socialnetwork.z.modules.content.model.entity.PostResponse;
import minhdoswe.socialnetwork.z.modules.content.model.entity.Post;
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
    PostResponse toPostResponse(Post post);

    void updatePostFromRequest(PostRequest postRequest, @MappingTarget Post existingPost);
}
