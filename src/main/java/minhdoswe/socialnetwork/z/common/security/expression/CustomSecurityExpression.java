package minhdoswe.socialnetwork.z.common.security.expression;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Comment;
import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Post;
import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.User;
import minhdoswe.socialnetwork.z.modules.content.internal.enums.Visibility;
import minhdoswe.socialnetwork.z.modules.content.internal.exception.comment.CommentNotFoundException;
import minhdoswe.socialnetwork.z.modules.content.internal.exception.post.post.PostNotFoundException;
import minhdoswe.socialnetwork.z.modules.content.internal.repository.CommentRepository;
import minhdoswe.socialnetwork.z.modules.relationship.internal.repository.FollowRepository;
import minhdoswe.socialnetwork.z.modules.content.internal.repository.PostRepository;
import minhdoswe.socialnetwork.z.common.util.SecurityUtils;
import org.springframework.stereotype.Component;

@Component("customSecurity")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final PostRepository postRepository;
    private final SecurityUtils securityUtils;
    private final FollowRepository followRepository;
    private final CommentRepository commentRepository;

    public boolean isPostOwner(Long postId) {
        User user = securityUtils.getCurrentUser();
        // 2. Query DB directly
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        return post.getUser().getId().equals(user.getId());
    }

    public boolean isCommentOwner(Long commentId) {
        User user = securityUtils.getCurrentUser();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));

        return comment.getUser().equals(user);
    }

    public boolean canViewPost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        return checkPostAccess(post);
    }

    private boolean checkPostAccess(Post post) {
        User user = securityUtils.getCurrentUser();
        if (post.getVisibility() == Visibility.PUBLIC) return true;

        if (post.getUser().getId().equals(user.getId())) return true;

        if (post.getVisibility() == Visibility.PRIVATE) {
            return followRepository.existsFollowByFollowerIdAndTargetId(user.getId(), post.getUser().getId());
        }

        return false;
    }

    public boolean canViewComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("comment not found"));

        return checkPostAccess(comment.getPost());
    }
}
