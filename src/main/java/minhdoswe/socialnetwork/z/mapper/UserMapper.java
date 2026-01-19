package minhdoswe.socialnetwork.z.mapper;

import minhdoswe.socialnetwork.z.dto.request.auth.RegisterRequest;
import minhdoswe.socialnetwork.z.dto.response.UserDTO;
import minhdoswe.socialnetwork.z.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface UserMapper {

    User toUser(RegisterRequest registerRequest);

    UserDTO toUserDTO(User user);
}
