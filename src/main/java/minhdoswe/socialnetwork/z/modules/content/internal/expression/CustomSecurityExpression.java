package minhdoswe.socialnetwork.z.modules.content.internal.expression;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.common.annotation.CurrentUserId;
import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Comment;
import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Post;
import minhdoswe.socialnetwork.z.modules.content.internal.enums.Visibility;
import minhdoswe.socialnetwork.z.modules.content.internal.exception.comment.CommentNotFoundException;
import minhdoswe.socialnetwork.z.modules.content.internal.exception.post.post.PostNotFoundException;
import minhdoswe.socialnetwork.z.modules.content.internal.repository.CommentRepository;
import minhdoswe.socialnetwork.z.modules.content.internal.repository.PostRepository;
import minhdoswe.socialnetwork.z.modules.relationship.RelationshipAPI;
import org.springframework.stereotype.Component;

@Component("customSecurity")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final RelationshipAPI relationshipAPI;

    public boolean isPostOwner(Long currentUserId, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        return post.getUserId().equals(currentUserId);
    }

    public boolean isCommentOwner(Long currentUserId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));

        return comment.getUserId().equals(currentUserId);
    }

    public boolean canViewPost(Long currentUserId, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        return checkPostAccess(currentUserId, post);
    }

    private boolean checkPostAccess(Long currentUserId, Post post) {
        if (post.getVisibility() == Visibility.PUBLIC) return true;

        if (post.getUserId().equals(currentUserId)) return true;

        if (post.getVisibility() == Visibility.PRIVATE) {
            return relationshipAPI.isFollower(currentUserId, post.getUserId());
        }

        return false;
    }

    public boolean canViewComment(Long currentUserId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("comment not found"));

        return checkPostAccess(currentUserId, comment.getPost());
    }

    private Long getCurrentUserId(@CurrentUserId Long currentUserId) {
        return currentUserId;
    }
}
