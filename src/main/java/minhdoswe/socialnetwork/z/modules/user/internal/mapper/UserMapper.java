package minhdoswe.socialnetwork.z.modules.user.internal.mapper;

import minhdoswe.socialnetwork.z.modules.user.dto.UserDTO;
import minhdoswe.socialnetwork.z.modules.user.internal.model.dto.auth.RegisterRequest;
import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.User;
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
