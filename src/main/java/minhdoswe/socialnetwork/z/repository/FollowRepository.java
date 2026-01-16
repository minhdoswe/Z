package minhdoswe.socialnetwork.z.repository;

import minhdoswe.socialnetwork.z.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsFollowByFollowerIdAndTargetId(Long followerId, Long targetId);

    void deleteByFollowerIdAndTargetId(Long followerId, Long targetId);
}
