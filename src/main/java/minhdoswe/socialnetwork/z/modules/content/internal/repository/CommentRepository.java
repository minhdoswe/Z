package minhdoswe.socialnetwork.z.modules.content.internal.repository;

import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost_IdAndParent_Id(Long postId, Long parentId);

    List<Comment> findByParent_Id(Long parentId);

    @Query("""
        SELECT c FROM Comment c
        JOIN c.post p
        WHERE p.visibility = 'PUBLIC'
        OR p.user.id = :currentUserId
        OR p.visibility = 'PRIVATE'
            AND EXISTS (
                SELECT f FROM Follow f
                WHERE f.follower.id = :currentUserId
                    AND f.target = p.user
            )
""")
    List<Comment> findVisibleCommentsByTargetUser(Long currentUserId, Long targetUserId);
}
