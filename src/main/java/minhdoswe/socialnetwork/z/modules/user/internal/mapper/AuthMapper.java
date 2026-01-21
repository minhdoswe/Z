package minhdoswe.socialnetwork.z.modules.user.internal.mapper;

import minhdoswe.socialnetwork.z.modules.user.internal.model.dto.auth.LoginRequest;
import minhdoswe.socialnetwork.z.modules.user.internal.model.dto.auth.RecoverAccountRequest;
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
