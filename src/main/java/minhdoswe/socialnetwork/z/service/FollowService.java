package minhdoswe.socialnetwork.z.service;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.dto.response.FollowResponse;
import minhdoswe.socialnetwork.z.entity.Follow;
import minhdoswe.socialnetwork.z.entity.User;
import minhdoswe.socialnetwork.z.exception.follow.FollowAlreadyEstablishedException;
import minhdoswe.socialnetwork.z.exception.follow.UsersFollowThemselfException;
import minhdoswe.socialnetwork.z.repository.FollowRepository;
import minhdoswe.socialnetwork.z.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public FollowResponse follow(Long followerId, Long targetId) {

        if (followerId.equals(targetId)) {
            throw new UsersFollowThemselfException("Users cannot follow themselves");
        }

        if (followRepository.existsFollowByFollowerIdAndTargetId(followerId, targetId)) {
            throw new FollowAlreadyEstablishedException("Follow is already established");
        }

        User followerReference = userRepository.getReferenceById(followerId);
        User targetReference = userRepository.getReferenceById(targetId);

        Follow follow = Follow.builder()
                .follower(followerReference)
                .target(targetReference).build();

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
}
