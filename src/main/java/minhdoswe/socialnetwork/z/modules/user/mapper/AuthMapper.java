package minhdoswe.socialnetwork.z.modules.user.mapper;

import minhdoswe.socialnetwork.z.modules.user.model.dto.auth.LoginRequest;
import minhdoswe.socialnetwork.z.modules.user.model.dto.auth.RecoverAccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface AuthMapper {

    RecoverAccountRequest toRecoverAccountRequest(LoginRequest loginRequest);

    LoginRequest toLoginRequest(RecoverAccountRequest recoverAccountRequest);
}
