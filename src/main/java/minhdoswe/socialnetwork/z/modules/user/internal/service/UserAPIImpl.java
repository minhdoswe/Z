package minhdoswe.socialnetwork.z.modules.user.internal.service;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.user.api.UserAPI;
import minhdoswe.socialnetwork.z.modules.user.api.UserDTO;
import minhdoswe.socialnetwork.z.modules.user.internal.auth.user.UserNotFoundException;
import minhdoswe.socialnetwork.z.modules.user.internal.mapper.UserMapper;
import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.User;
import minhdoswe.socialnetwork.z.modules.user.internal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAPIImpl implements UserAPI {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Map<Long, UserDTO> getUserDTO(Set<Long> userIdSet) {

        List<User> userList = userRepository.findAllById(userIdSet);

        return userList.stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toMap(
                        UserDTO::getId,
                        Function.identity()
                        ));
    }

    @Override
    public UserDTO getUserDTO(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return userMapper.toUserDTO(user);
    }
}
