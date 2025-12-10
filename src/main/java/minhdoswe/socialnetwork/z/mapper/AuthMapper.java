package minhdoswe.socialnetwork.z.mapper;

import minhdoswe.socialnetwork.z.dto.request.auth.LoginRequest;
import minhdoswe.socialnetwork.z.dto.request.auth.RecoverAccountRequest;
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
