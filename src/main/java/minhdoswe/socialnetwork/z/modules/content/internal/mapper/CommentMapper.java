package minhdoswe.socialnetwork.z.modules.content.internal.mapper;

import minhdoswe.socialnetwork.z.modules.content.internal.model.dto.CommentResponse;
import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Comment;
import minhdoswe.socialnetwork.z.modules.user.UserAPI;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = {UserAPI.class}
)
public interface CommentMapper {

    @Mapping(source = "userId", target = "userDTO")
    CommentResponse toCommentResponse(Comment comment);
}
