package minhdoswe.socialnetwork.z.modules.auth.internal.mapper;

import minhdoswe.socialnetwork.z.modules.auth.internal.model.dto.LoginRequest;
import minhdoswe.socialnetwork.z.modules.auth.internal.model.dto.RegisterRequest;
import minhdoswe.socialnetwork.z.modules.user.api.dto.CreateUserRequest;
import minhdoswe.socialnetwork.z.modules.user.api.dto.UserAuthenticationDTO;
import minhdoswe.socialnetwork.z.modules.auth.internal.model.dto.RecoverAccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface AuthMapper {

    RecoverAccountRequest toRecoverAccountRequest(LoginRequest loginRequest);

    LoginRequest toLoginRequest(RecoverAccountRequest recoverAccountRequest);

    UserAuthenticationDTO toUserAuthenticationDTO(RegisterRequest registerRequest);

    CreateUserRequest toCreateUserRequest(RegisterRequest registerRequest);
}
