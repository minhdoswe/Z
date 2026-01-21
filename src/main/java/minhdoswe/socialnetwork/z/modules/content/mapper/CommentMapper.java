package minhdoswe.socialnetwork.z.modules.content.mapper;

import minhdoswe.socialnetwork.z.modules.content.model.dto.CommentResponse;
import minhdoswe.socialnetwork.z.modules.content.model.entity.Comment;
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
