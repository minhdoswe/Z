package minhdoswe.socialnetwork.z.modules.content.internal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minhdoswe.socialnetwork.z.common.util.JwtUtils;
import minhdoswe.socialnetwork.z.modules.content.internal.model.dto.PostRequest;
import minhdoswe.socialnetwork.z.modules.content.internal.model.dto.PostResponse;
import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Post;
import minhdoswe.socialnetwork.z.modules.user.internal.model.entity.User;
import minhdoswe.socialnetwork.z.modules.content.internal.enums.Visibility;
import minhdoswe.socialnetwork.z.modules.content.internal.exception.post.post.PostNotFoundException;
import minhdoswe.socialnetwork.z.modules.content.internal.mapper.PostMapper;
import minhdoswe.socialnetwork.z.modules.user.internal.service.UserService;
import minhdoswe.socialnetwork.z.modules.relationship.internal.repository.FollowRepository;
import minhdoswe.socialnetwork.z.modules.content.internal.repository.PostRepository;
import minhdoswe.socialnetwork.z.modules.user.internal.repository.UserRepository;
import minhdoswe.socialnetwork.z.common.security.expression.CustomSecurityExpression;
import minhdoswe.socialnetwork.z.common.util.SecurityUtils;
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
    private final JwtUtils jwtUtils;

    @Transactional
    public PostResponse create(Long userId, PostRequest postRequest) {
        Post post = postMapper.toPost(postRequest);
        post.setUserId(userId);
        Post savedPost = postRepository.save(post);

        return postMapper.toPostResponse(savedPost);
    }

    @Transactional
    @PreAuthorize("@customSecurity.isPostOwner(#postId)")
    public void delete(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        postRepository.delete(post);
    }

    @Transactional
    @PreAuthorize("@customSecurity.isPostOwner(#postId)")
    public PostResponse modify(Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        postMapper.updatePostFromRequest(postRequest, post);

        postRepository.save(post);

        return postMapper.toPostResponse(post);
    }

    public List<PostResponse> findByUserId(Long targetId) {

        Long userId = securityUtils.getCurrentUser().getId();

        boolean isOwner = userId.equals(targetId);

        if (isOwner) {
            return fetchByOwnerIdOrFollowerId(targetId);
        }

        log.info(userId + "                " + targetId);

        boolean isFollower = followRepository.existsFollowByFollowerIdAndTargetId(userId, targetId);

        User targetUser = userService.getUserById(targetId);

        if (isFollower) {
            return fetchByOwnerIdOrFollowerId(targetId);
        }

        return fetchByNonFollowerId(targetId);
    }

    private List<PostResponse> fetchByOwnerIdOrFollowerId(Long userId) {

        return postRepository.findByUser_Id(userId)
                .stream().map(postMapper::toPostResponse)
                .toList();
    }

    private List<PostResponse> fetchByNonFollowerId(Long userId) {
        return postRepository.findByUser_IdAndVisibility(userId, Visibility.PUBLIC)
                .stream().map(postMapper::toPostResponse)
                .toList();
    }

    @PreAuthorize("@customSecurity.canViewPost(#postId)")
    public PostResponse getById(Long postId) {
        Post post =  postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        return postMapper.toPostResponse(post);
    }



}
