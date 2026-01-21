package minhdoswe.socialnetwork.z.modules.engagement.internal.repository;

import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Post;
import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.User;
import minhdoswe.socialnetwork.z.modules.engagement.internal.model.entity.PostVote;
import minhdoswe.socialnetwork.z.modules.engagement.internal.enums.PostVoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostVoteRepository extends JpaRepository<PostVote, Long> {

    boolean existsByPostAndUser(Post post, User user);

    void deleteByPostAndUser(Post post, User user);

    Optional<PostVote> findByPostAndUser(Post post, User user);

    Long countByPostAndPostVoteType(Post post, PostVoteType postVoteType);

    List<PostVote> findByPostAndPostVoteType(Post post, PostVoteType postVoteType);

    List<PostVote> findByUser(User user);

    @Query(
            """
        SELECT v FROM PostVote v
        JOIN v.post p
        WHERE v.user.id = :targetUserId
        AND p.visibility = 'PUBLIC'
        OR p.user.id = :currentUserId
        OR p.visibility = 'PRIVATE'
        AND EXISTS (
            SELECT f FROM Follow f
            WHERE f.follower.id = :currentUserId
            AND f.target.id = p.user.id
        )
"""
    )
    List<PostVote> findVisibleVotesByTargetUser(Long currentUserId, Long targetUserId);
}
