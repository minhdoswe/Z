package minhdoswe.socialnetwork.z.modules.user.internal.adapter;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.user.api.dto.CreateUserRequest;
import minhdoswe.socialnetwork.z.modules.user.api.dto.CreateUserResponse;
import minhdoswe.socialnetwork.z.modules.user.api.dto.UserAuthenticationDTO;
import minhdoswe.socialnetwork.z.modules.user.api.UserPort;
import minhdoswe.socialnetwork.z.modules.user.api.dto.UserDTO;
import minhdoswe.socialnetwork.z.modules.user.internal.exception.UserAlreadyExistsException;
import minhdoswe.socialnetwork.z.modules.user.internal.exception.UserNotFoundException;
import minhdoswe.socialnetwork.z.modules.user.internal.mapper.UserMapper;
import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.User;
import minhdoswe.socialnetwork.z.modules.user.internal.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAdapter implements UserPort {

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

    @Override
    public UserAuthenticationDTO getUserInfo(String identifier) {

        User user = userRepository.findByIdentifierIncludingDeleted(identifier)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        return userMapper.userAuthenticationDTO(user);
    }

    @Override
    public UserAuthenticationDTO getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        return userMapper.userAuthenticationDTO(user);
    }

    @Override
    public boolean existByIdentifierIncludedDeleted(String username, String email, String phoneNumber) {
        if (userRepository.existsByUsernameIncludingDeleted(username)) {
            throw new UserAlreadyExistsException("Username: " + username + " is already taken");
        }
        if (userRepository.existsByEmailIncludingDeleted(email)) {
            throw new UserAlreadyExistsException("Email is already linked to another account");
        }
        if (userRepository.existsByPhoneNumberIncludingDeleted(phoneNumber)) {
            throw new UserAlreadyExistsException("Phone number is already linked to another account");
        }

        return true;
    }

    @Override
    @Transactional
    public CreateUserResponse saveUser(CreateUserRequest createUserRequest) {

        User user = userMapper.toUser(createUserRequest);
        return userMapper.toCreateUserResponse(userRepository.save(user));
    }

    @Override
    public UserAuthenticationDTO findByIdentifierIncludedDeleted(String identifier) {
        User user = userRepository.findByIdentifierIncludingDeleted(identifier)
                .orElseThrow(() -> new UserNotFoundException("user not exists"));

        return userMapper.userAuthenticationDTO(user);
    }

    @Override
    public String getPassword(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not exists"));

        return user.getPassword();
    }

    @Override
    @Transactional
    public void deactivateUser(Long userId) {
        userRepository.deactivateUser(userId);
    }

}
