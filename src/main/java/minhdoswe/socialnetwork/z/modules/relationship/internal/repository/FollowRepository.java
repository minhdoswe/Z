package minhdoswe.socialnetwork.z.modules.relationship.internal.repository;

import minhdoswe.socialnetwork.z.modules.relationship.internal.model.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsFollowByFollowerIdAndTargetId(Long followerId, Long targetId);

    void deleteByFollowerIdAndTargetId(Long followerId, Long targetId);

    List<Follow> findByTargetId(Long targetId);

    List<Follow> getAllByFollowerId(Long followerId);

    @Modifying
    @Query("""
        UPDATE Follow f
        SET f.hidden = true
        WHERE f.followerId = :userId
        OR f.targetId = :userId
"""
    )
    int hideAllByUser(Long userId);
}

