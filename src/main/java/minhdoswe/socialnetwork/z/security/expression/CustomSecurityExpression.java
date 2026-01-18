package minhdoswe.socialnetwork.z.security.expression;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.entity.Post;
import minhdoswe.socialnetwork.z.entity.User;
import minhdoswe.socialnetwork.z.enums.Visibility;
import minhdoswe.socialnetwork.z.exception.post.PostNotFoundException;
import minhdoswe.socialnetwork.z.repository.FollowRepository;
import minhdoswe.socialnetwork.z.repository.PostRepository;
import minhdoswe.socialnetwork.z.service.FollowService;
import minhdoswe.socialnetwork.z.service.PostService;
import minhdoswe.socialnetwork.z.util.SecurityUtils;
import org.springframework.stereotype.Component;

@Component("customSecurity")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final PostRepository postRepository;
    private final SecurityUtils securityUtils;
    private final FollowRepository followRepository;

    public boolean isPostOwner(Long postId) {
        User user = securityUtils.getCurrentUser();
        // 2. Query DB directly
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        return post.getUser().getId().equals(user.getId());
    }

    public boolean canView(Long postId) {
        User user = securityUtils.getCurrentUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        if (post.getVisibility() == Visibility.PUBLIC) return true;

        if (post.getUser().getId().equals(user.getId())) return true;

        if (post.getVisibility() == Visibility.PRIVATE) {
            return followRepository.existsFollowByFollowerIdAndTargetId(user.getId(), post.getUser().getId());
        }

        return false;
    }
}
