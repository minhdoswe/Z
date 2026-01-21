package minhdoswe.socialnetwork.z.modules.relationship.repository;

import minhdoswe.socialnetwork.z.modules.relationship.model.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsFollowByFollowerIdAndTargetId(Long followerId, Long targetId);

    void deleteByFollowerIdAndTargetId(Long followerId, Long targetId);

    List<Follow> findByTarget_Id(Long targetId);
}
