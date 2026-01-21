package minhdoswe.socialnetwork.z.modules.content.internal.mapper;

import minhdoswe.socialnetwork.z.modules.content.internal.model.dto.CommentResponse;
import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface CommentMapper {

    @Mapping(source = "user", target = "userDTO")
    CommentResponse toCommentResponse(Comment comment);
}
