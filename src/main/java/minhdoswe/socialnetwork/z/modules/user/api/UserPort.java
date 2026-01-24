package minhdoswe.socialnetwork.z.modules.user.api;

import minhdoswe.socialnetwork.z.modules.user.api.dto.CreateUserRequest;
import minhdoswe.socialnetwork.z.modules.user.api.dto.CreateUserResponse;
import minhdoswe.socialnetwork.z.modules.user.api.dto.UserAuthenticationDTO;
import minhdoswe.socialnetwork.z.modules.user.api.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public interface UserPort {

    Map<Long, UserDTO> getUserDTO(Set<Long> userIdSet);

    UserDTO getUserDTO(Long userId);

    UserAuthenticationDTO getUserInfo(String identifier);

    UserAuthenticationDTO getUserInfo(Long userId);

    boolean existByIdentifierIncludedDeleted(String username, String email, String password);

    CreateUserResponse saveUser(CreateUserRequest createUserRequest);

    UserAuthenticationDTO findByIdentifierIncludedDeleted(String identifier);

    String getPassword(Long userId);

    void deactivateUser(Long userId);
}
