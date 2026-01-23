package minhdoswe.socialnetwork.z.modules.user.api;

import java.util.Map;
import java.util.Set;

public interface UserAPI {

    Map<Long, UserDTO> getUserDTO(Set<Long> userIdSet);

    UserDTO getUserDTO(Long userId);
}
