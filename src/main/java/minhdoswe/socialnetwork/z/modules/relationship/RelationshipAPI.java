package minhdoswe.socialnetwork.z.modules.relationship;

import java.util.List;

public interface RelationshipAPI {

    boolean isFollower(Long followerId, long targetId);

    List<Long> getFollowingIds(Long followerId);
}
