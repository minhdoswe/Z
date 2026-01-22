//package minhdoswe.socialnetwork.z.modules.content.internal.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import minhdoswe.socialnetwork.z.common.annotation.CurrentUserId;
//import minhdoswe.socialnetwork.z.common.util.JwtUtils;
//import minhdoswe.socialnetwork.z.modules.content.internal.helper.PostEnricher;
//import minhdoswe.socialnetwork.z.modules.content.internal.model.dto.PostRequest;
//import minhdoswe.socialnetwork.z.modules.content.internal.model.dto.PostResponse;
//import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Post;
//import minhdoswe.socialnetwork.z.modules.content.internal.enums.Visibility;
//import minhdoswe.socialnetwork.z.modules.content.internal.exception.post.post.PostNotFoundException;
//import minhdoswe.socialnetwork.z.modules.content.internal.mapper.PostMapper;
//import minhdoswe.socialnetwork.z.modules.content.internal.repository.PostRepository;
//import minhdoswe.socialnetwork.z.modules.content.internal.expression.CustomSecurityExpression;
//import minhdoswe.socialnetwork.z.modules.relationship.RelationshipAPI;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class PostService {
//
//    private final PostRepository postRepository;
//    private final PostMapper postMapper;
//    private final CustomSecurityExpression customSecurity;
//    private final JwtUtils jwtUtils;
//    private final RelationshipAPI relationshipAPI;
//    private final PostEnricher postEnricher;
//
//    @Transactional
//    public PostResponse create(Long userId, PostRequest postRequest) {
//        Post post = postMapper.toPost(postRequest);
//        post.setUserId(userId);
//        Post savedPost = postRepository.save(post);
//
//        return postMapper.toPostResponse(savedPost);
//    }
//
//    @Transactional
//    @PreAuthorize("@customSecurity.isPostOwner(#currentUserId, #postId)")
//    public void delete(Long currentUserId, Long postId) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new RuntimeException("Post not found"));
//
//        postRepository.delete(post);
//    }
//
//    @Transactional
//    @PreAuthorize("@customSecurity.isPostOwner(#currentUserId, #postId)")
//    public PostResponse modify(Long postId, PostRequest postRequest) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new RuntimeException("Post not found"));
//
//        postMapper.updatePostFromRequest(postRequest, post);
//
//        postRepository.save(post);
//
//        return postMapper.toPostResponse(post);
//    }
//
//    public List<PostResponse> findByUserId(Long currentUserId, Long targetId) {
//
//        boolean isOwner = currentUserId.equals(targetId);
//
//        if (isOwner) {
//            return fetchByOwnerIdOrFollowerId(targetId);
//        }
//
//        boolean isFollower = relationshipAPI.isFollower(currentUserId, targetId);
//
//        if (isFollower) {
//            return fetchByOwnerIdOrFollowerId(targetId);
//        }
//
//        return fetchByNonFollowerId(targetId);
//    }
//
//    private List<PostResponse> fetchByOwnerIdOrFollowerId(Long userId) {
//
//        List<Post> postList = postRepository.findByUserId(userId);
//
//        return postEnricher.enrichList(postList);
//    }
//
//    private List<PostResponse> fetchByNonFollowerId(Long userId) {
//        List<Post> postList =  postRepository.findByUserIdAndVisibility(userId, Visibility.PUBLIC);
//
//        return postEnricher.enrichList(postList);
//    }
//
//    @PreAuthorize("@customSecurity.canViewPost(#currentUserId, #postId)")
//    public PostResponse getById(Long currentUserId, Long postId) {
//        Post post =  postRepository.findById(postId)
//                .orElseThrow(() -> new PostNotFoundException("Post not found"));
//
//        return postEnricher.enrichOne(post);
//    }
//
//
//
//}
