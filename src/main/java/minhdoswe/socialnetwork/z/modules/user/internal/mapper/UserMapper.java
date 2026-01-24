package minhdoswe.socialnetwork.z.modules.user.internal.mapper;

import minhdoswe.socialnetwork.z.modules.user.api.dto.CreateUserRequest;
import minhdoswe.socialnetwork.z.modules.user.api.dto.CreateUserResponse;
import minhdoswe.socialnetwork.z.modules.user.api.dto.UserAuthenticationDTO;
import minhdoswe.socialnetwork.z.modules.user.api.dto.UserDTO;
import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.User;
import minhdoswe.socialnetwork.z.modules.user.internal.model.enums.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface UserMapper {

    default String map(Role role) {
        return role.name();
    }

    default Role map(String string) {
        return Role.valueOf(string);
    }

    UserAuthenticationDTO userAuthenticationDTO(User user);

    UserDTO toUserDTO(User user);

    User toUser(CreateUserRequest createUserRequest);

    CreateUserResponse toCreateUserResponse(User user);
}
