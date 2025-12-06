package minhdoswe.socialnetwork.z.mapper;

import minhdoswe.socialnetwork.z.dto.request.RegisterRequest;
import minhdoswe.socialnetwork.z.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)

public interface UserMapper {

    User toUser(RegisterRequest registerRequest);
}
