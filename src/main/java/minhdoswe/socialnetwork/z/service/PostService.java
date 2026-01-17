package minhdoswe.socialnetwork.z.service;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.dto.request.PostRequest;
import minhdoswe.socialnetwork.z.dto.response.PostResponse;
import minhdoswe.socialnetwork.z.entity.Post;
import minhdoswe.socialnetwork.z.entity.User;
import minhdoswe.socialnetwork.z.enums.Visibility;
import minhdoswe.socialnetwork.z.exception.post.PostNotFoundException;
import minhdoswe.socialnetwork.z.mapper.PostMapper;
import minhdoswe.socialnetwork.z.repository.FollowRepository;
import minhdoswe.socialnetwork.z.repository.PostRepository;
import minhdoswe.socialnetwork.z.repository.UserRepository;
import minhdoswe.socialnetwork.z.security.expression.CustomSecurityExpression;
import minhdoswe.socialnetwork.z.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final SecurityUtils securityUtils;
    private final PostMapper postMapper;
    private final CustomSecurityExpression customSecurity;
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createPost(PostRequest postRequest) {
        User user = securityUtils.getCurrentUser();
        Post post = postMapper.toPost(postRequest);
        post.setUser(user);
        postRepository.save(post);
    }

    @Transactional
    @PreAuthorize("@customSecurity.isPostOwner(#postId, authentication)")
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        postRepository.delete(post);
    }

    @Transactional
    @PreAuthorize("@customSecurity.isPostOwner(#postId, authentication)")
    public void modifyPost(Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        postMapper.updatePostFromRequest(postRequest, post);

        postRepository.save(post);
    }

    public List<PostResponse> findPostByUserId(Long targetId) {

        User user = securityUtils.getCurrentUser();
        Long userId = user.getId();

        boolean isOwner = userId.equals(targetId);

        if (isOwner) {
            return fetchPostsByOwnerOrFollower(user);
        }

        boolean isFollower = followRepository.existsFollowByFollowerIdAndTargetId(userId, targetId);

        User targetUser = userRepository.getReferenceById(targetId);

        if (isFollower) {
            return fetchPostsByOwnerOrFollower(targetUser);
        }

        return fetchPostsByNonFollower(targetUser);
    }

    private List<PostResponse> fetchPostsByOwnerOrFollower(User user) {
        return postRepository.findPostsByUser(user)
                .stream().map(postMapper::toPostResponse)
                .toList();
    }

    private List<PostResponse> fetchPostsByNonFollower(User user) {
        return postRepository.findPostsByUserAndVisibility(user, Visibility.PUBLIC)
                .stream().map(postMapper::toPostResponse)
                .toList();
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
    }



}
