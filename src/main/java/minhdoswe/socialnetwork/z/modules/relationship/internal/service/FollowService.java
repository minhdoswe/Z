package minhdoswe.socialnetwork.z.modules.relationship.internal.service;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.relationship.internal.model.dto.FollowResponse;
import minhdoswe.socialnetwork.z.modules.relationship.internal.model.entity.Follow;
import minhdoswe.socialnetwork.z.modules.relationship.internal.exception.follow.FollowAlreadyEstablishedException;
import minhdoswe.socialnetwork.z.modules.relationship.internal.exception.follow.UsersFollowThemselfException;
import minhdoswe.socialnetwork.z.modules.relationship.internal.repository.FollowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    @Transactional
    public FollowResponse follow(Long followerId, Long targetId) {

        if (followerId.equals(targetId)) {
            throw new UsersFollowThemselfException("Users cannot follow themselves");
        }

        if (followRepository.existsFollowByFollowerIdAndTargetId(followerId, targetId)) {
            throw new FollowAlreadyEstablishedException("Follow is already established");
        }

        Follow follow = Follow.builder()
                .followerId(followerId)
                .targetId(targetId)
                .build();

        followRepository.save(follow);

        FollowResponse followResponse = FollowResponse.builder()
                .isFollowing(true)
                .isFriend(false)
                .build();

        if (followRepository.existsFollowByFollowerIdAndTargetId(targetId, followerId)) {
            followResponse.setFriend(true);
        }

        return followResponse;
    }

    @Transactional
    public FollowResponse unfollow(Long followerId, Long targetId) {

        followRepository.deleteByFollowerIdAndTargetId(followerId, targetId);

        return FollowResponse.builder()
                .isFollowing(false)
                .isFriend(false)
                .build();
    }

    public boolean isFriend(Long user1Id, Long user2Id) {

        return followRepository.existsFollowByFollowerIdAndTargetId(user1Id, user2Id)
        && followRepository.existsFollowByFollowerIdAndTargetId(user2Id, user1Id);
    }

//    public List<UserDTO> getFollowers(Long userId) {
//
//        return followRepository.findByTarget_Id(userId).stream()
//                .map(follow -> userMapper.toUserDTO(follow.getFollowerId())).toList();
//    }

//    public void deleteFollower(Long followerId) {
//
//        Long currentUserId = securityUtils.getCurrentUser().getId();
//        followRepository.deleteByFollowerIdAndTargetId(currentUserId, followerId);
//    }
}
