package minhdoswe.socialnetwork.z.repository;

import minhdoswe.socialnetwork.z.entity.Post;
import minhdoswe.socialnetwork.z.entity.User;
import minhdoswe.socialnetwork.z.entity.Vote;
import minhdoswe.socialnetwork.z.enums.VoteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    boolean existsVoteByPostAndUser(Post post, User user);

    void deleteVoteByPostAndUser(Post post, User user);

    Optional<Vote> findVoteByPostAndUser(Post post, User user);

    Long countVotesByPostAndVoteStatus(Post post, VoteStatus voteStatus);

    List<Vote> findVotesByPostAndVoteStatus(Post post, VoteStatus voteStatus);

    List<Vote> findVotesByUser(User user);

    @Query(
            """
        SELECT v FROM Vote v
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
    List<Vote> findVisibleVotesByTargetUser(Long currentUserId, Long targetUserId);
}
