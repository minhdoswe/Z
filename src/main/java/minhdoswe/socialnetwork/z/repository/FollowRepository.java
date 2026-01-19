package minhdoswe.socialnetwork.z.repository;

import minhdoswe.socialnetwork.z.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsFollowByFollowerIdAndTargetId(Long followerId, Long targetId);

    void deleteByFollowerIdAndTargetId(Long followerId, Long targetId);

    List<Follow> findByTarget_Id(Long targetId);
}
