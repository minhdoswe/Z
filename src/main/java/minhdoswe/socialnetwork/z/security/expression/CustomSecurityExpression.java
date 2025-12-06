package minhdoswe.socialnetwork.z.security.expression;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.entity.Post;
import minhdoswe.socialnetwork.z.repository.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("customSecurity")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final PostRepository postRepository;

    public boolean isPostOwner(Long postId, Authentication authentication) {

        String currentUsername = authentication.getName();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return post.getUser().getUsername().equals(currentUsername);
    }
}
