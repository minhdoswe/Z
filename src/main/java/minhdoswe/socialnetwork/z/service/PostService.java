package minhdoswe.socialnetwork.z.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final SecurityUtils securityUtils;
    private final PostMapper postMapper;
    private final CustomSecurityExpression customSecurity;
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public void createPost(PostRequest postRequest) {
        User user = securityUtils.getCurrentUser();
        Post post = postMapper.toPost(postRequest);
        post.setUser(user);
        postRepository.save(post);
    }

    @Transactional
    @PreAuthorize("@customSecurity.isPostOwner(#postId)")
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        postRepository.delete(post);
    }

    @Transactional
    @PreAuthorize("@customSecurity.isPostOwner(#postId)")
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

        log.info(userId + "                " + targetId);

        boolean isFollower = followRepository.existsFollowByFollowerIdAndTargetId(userId, targetId);

        User targetUser = userService.getUserById(targetId);

        if (isFollower) {
            return fetchPostsByOwnerOrFollower(targetUser);
        }

        return fetchPostsByNonFollower(targetUser);
    }

    private List<PostResponse> fetchPostsByOwnerOrFollower(User user) {
        log.info("in method fetch post by owner of follower  " + user.getId());
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
