//package minhdoswe.socialnetwork.z.modules.content.internal.service;
//
//import lombok.RequiredArgsConstructor;
//import minhdoswe.socialnetwork.z.modules.content.internal.helper.CommentEnricher;
//import minhdoswe.socialnetwork.z.modules.content.internal.model.dto.CommentRequest;
//import minhdoswe.socialnetwork.z.modules.content.internal.model.dto.CommentResponse;
//import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Comment;
//import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Post;
//import minhdoswe.socialnetwork.z.modules.content.internal.exception.comment.CommentNotFoundException;
//import minhdoswe.socialnetwork.z.modules.content.internal.exception.post.post.PostNotFoundException;
//import minhdoswe.socialnetwork.z.modules.content.internal.mapper.CommentMapper;
//import minhdoswe.socialnetwork.z.modules.content.internal.repository.CommentRepository;
//import minhdoswe.socialnetwork.z.modules.content.internal.repository.PostRepository;
//import minhdoswe.socialnetwork.z.modules.relationship.api.RelationshipAPI;
//import minhdoswe.socialnetwork.z.modules.user.api.UserAPI;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class CommentService {
//
//    private final CommentMapper commentMapper;
//    private final CommentRepository commentRepository;
//    private final PostRepository postRepository;
//    private final UserAPI userAPI;
//    private final CommentEnricher commentEnricher;
//    private final RelationshipAPI relationshipAPI;
//
//    @PreAuthorize("@customSecurity.canViewPost(#postId)")
//    public CommentResponse create(Long currentUserId, Long postId, CommentRequest commentRequest) {
//
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new PostNotFoundException("post not found"));
//
//        Comment comment = Comment.builder()
//                .content(commentRequest.getContent())
//                .parent(null)
//                .userId(currentUserId)
//                .post(post)
//                .build();
//
//        commentRepository.save(comment);
//
//        return commentMapper.toCommentResponse(comment);
//    }
//
//    @PreAuthorize("@customSecurity.isCommentOwner(#commentId)")
//    public CommentResponse modify(Long commentId, CommentRequest commentRequest) {
//
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new CommentNotFoundException("comment not found"));
//
//        comment.setContent(commentRequest.getContent());
//
//        commentRepository.save(comment);
//
//        return commentMapper.toCommentResponse(comment);
//    }
//
//    @PreAuthorize("@customSecurity.isCommentOwner(#commentId)")
//    public void delete(Long commentId) {
//
//        commentRepository.deleteById(commentId);
//    }
//
//    @PreAuthorize("@customSecurity.canViewPost(#postId)")
//    public List<CommentResponse> getByPost(Long postId) {
//
//        List<Comment> commentList = commentRepository.findByPost_IdAndParent_Id(postId, null);
//
//        return commentEnricher.enrichList(commentList);
//    }
//
//    @PreAuthorize("@customSecurity.canViewComment(#commentId)")
//    public List<CommentResponse> getByComment(Long commentId) {
//
//        List<Comment> commentList = commentRepository.findByParent_Id(commentId);
//
//        return commentEnricher.enrichList(commentList);
//    }
//
//    @PreAuthorize("@customSecurity.canViewComment(#commentId)")
//    public CommentResponse reply(Long currentUserId, Long commentId, CommentRequest commentRequest) {
//
//        Comment parent = commentRepository.findById(commentId)
//                .orElseThrow(() -> new CommentNotFoundException("comment not found"));
//
//        Comment comment = Comment.builder()
//                .content(commentRequest.getContent())
//                .parent(parent)
//                .userId(currentUserId)
//                .post(parent.getPost())
//                .build();
//
//        commentRepository.save(comment);
//
//        return commentMapper.toCommentResponse(comment);
//    }
//
//    public List<CommentResponse> getByUser(Long currentUserId, Long targetUserId) {
//
//        List<Long> followingIds = relationshipAPI.getFollowingIds(currentUserId);
//
//        List<Comment> commentResponseList = commentRepository.findVisibleCommentsByTargetUser(currentUserId, targetUserId, followingIds);
//
//        return commentEnricher.enrichList(commentResponseList);
//    }
//}
