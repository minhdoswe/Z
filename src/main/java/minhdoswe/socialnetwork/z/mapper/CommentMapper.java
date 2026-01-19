package minhdoswe.socialnetwork.z.mapper;

import minhdoswe.socialnetwork.z.dto.response.CommentResponse;
import minhdoswe.socialnetwork.z.entity.Comment;
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
