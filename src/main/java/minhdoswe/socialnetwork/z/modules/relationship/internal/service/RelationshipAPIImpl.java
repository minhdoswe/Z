package minhdoswe.socialnetwork.z.modules.relationship.internal.service;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.relationship.api.RelationshipAPI;
import minhdoswe.socialnetwork.z.modules.relationship.internal.model.entity.Follow;
import minhdoswe.socialnetwork.z.modules.relationship.internal.repository.FollowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelationshipAPIImpl implements RelationshipAPI {

    private final FollowRepository followRepository;

    @Override
    public boolean isFollower(Long followerId, long targetId) {

        return followRepository.existsFollowByFollowerIdAndTargetId(followerId, targetId);
    }

    @Override
    public List<Long> getFollowingIds(Long followerId) {

        return followRepository.getAllByFollowerId(followerId)
                .stream().map(Follow::getTargetId)
                .toList();
    }
}
