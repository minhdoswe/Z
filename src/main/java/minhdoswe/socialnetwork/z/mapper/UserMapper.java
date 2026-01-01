package minhdoswe.socialnetwork.z.mapper;

import minhdoswe.socialnetwork.z.dto.request.auth.RegisterRequest;
import minhdoswe.socialnetwork.z.dto.response.author.AuthorDTO;
import minhdoswe.socialnetwork.z.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface UserMapper {

    User toUser(RegisterRequest registerRequest);

    AuthorDTO toAuthorDTO(User user);
}
