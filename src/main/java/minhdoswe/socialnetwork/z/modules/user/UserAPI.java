package minhdoswe.socialnetwork.z.modules.user;

import minhdoswe.socialnetwork.z.modules.user.dto.UserDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserAPI {

    Map<Long, UserDTO> getUserDTO(Set<Long> userIdSet);

    UserDTO getUserDTO(Long userId);
}
