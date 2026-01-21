package minhdoswe.socialnetwork.z.modules.content.service;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.content.model.entity.CommentRequest;
import minhdoswe.socialnetwork.z.modules.content.model.dto.CommentResponse;
import minhdoswe.socialnetwork.z.modules.content.model.entity.Comment;
import minhdoswe.socialnetwork.z.modules.content.model.entity.Post;
import minhdoswe.socialnetwork.z.modules.user.model.entity.User;
import minhdoswe.socialnetwork.z.modules.content.exception.comment.CommentNotFoundException;
import minhdoswe.socialnetwork.z.modules.content.exception.post.post.PostNotFoundException;
import minhdoswe.socialnetwork.z.modules.content.mapper.CommentMapper;
import minhdoswe.socialnetwork.z.modules.content.repository.CommentRepository;
import minhdoswe.socialnetwork.z.modules.content.repository.PostRepository;
import minhdoswe.socialnetwork.z.common.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final SecurityUtils securityUtils;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @PreAuthorize("@customSecurity.canViewPost(#postId)")
    public CommentResponse create(Long postId, CommentRequest commentRequest) {
        User user = securityUtils.getCurrentUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("post not found"));

        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .parent(null)
                .user(user)
                .post(post)
                .build();

        commentRepository.save(comment);

        return commentMapper.toCommentResponse(comment);
    }

    @PreAuthorize("@customSecurity.isCommentOwner(#commentId)")
    public CommentResponse modify(Long commentId, CommentRequest commentRequest) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("comment not found"));

        comment.setContent(commentRequest.getContent());

        commentRepository.save(comment);

        return commentMapper.toCommentResponse(comment);
    }

    @PreAuthorize("@customSecurity.isCommentOwner(#commentId)")
    public void delete(Long commentId) {

        commentRepository.deleteById(commentId);
    }

    @PreAuthorize("@customSecurity.canViewPost(#postId)")
    public List<CommentResponse> getByPost(Long postId) {

        List<Comment> commentList = commentRepository.findByPost_IdAndParent_Id(postId, null);

        return commentList.stream().map(commentMapper::toCommentResponse)
                .toList();
    }

    @PreAuthorize("@customSecurity.canViewComment(#commentId)")
    public List<CommentResponse> getByComment(Long commentId) {

        List<Comment> commentList = commentRepository.findByParent_Id(commentId);

        return commentList.stream().map(commentMapper::toCommentResponse)
                .toList();
    }

    @PreAuthorize("@customSecurity.canViewComment(#commentId)")
    public CommentResponse reply(Long commentId, CommentRequest commentRequest) {
        User user = securityUtils.getCurrentUser();
        Comment parent = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("comment not found"));

        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .parent(parent)
                .user(user)
                .post(parent.getPost())
                .build();

        commentRepository.save(comment);

        return commentMapper.toCommentResponse(comment);
    }

    public List<CommentResponse> getByUser(Long targetUserId) {

        Long currentUserId = securityUtils.getCurrentUser().getId();
        List<Comment> commentResponseList = commentRepository.findVisibleCommentsByTargetUser(currentUserId, targetUserId);

        return commentResponseList.stream()
                .map(commentMapper::toCommentResponse)
                .toList();
    }
}
