//package minhdoswe.socialnetwork.z.modules.engagement.internal.service;
//
//import lombok.RequiredArgsConstructor;
//import minhdoswe.socialnetwork.z.modules.engagement.internal.model.dto.CommentVoteRequest;
//import minhdoswe.socialnetwork.z.modules.engagement.internal.model.dto.CommentVoteResponse;
//import minhdoswe.socialnetwork.z.modules.engagement.internal.model.entity.CommentVote;
//import minhdoswe.socialnetwork.z.modules.engagement.internal.enums.CommentVoteType;
//
//import minhdoswe.socialnetwork.z.modules.content.internal.mapper.CommentMapper;
//import minhdoswe.socialnetwork.z.modules.engagement.internal.mapper.CommentVoteMapper;
//import minhdoswe.socialnetwork.z.modules.content.internal.repository.CommentRepository;
//import minhdoswe.socialnetwork.z.modules.engagement.internal.repository.CommentVoteRepository;
//import minhdoswe.socialnetwork.z.modules.engagement.internal.repository.PostVoteRepository;
//import minhdoswe.socialnetwork.z.common.util.SecurityUtils;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Service
//public class CommentVoteService {
//
//    private final CommentRepository commentRepository;
//    private final SecurityUtils securityUtils;
//    private final CommentVoteRepository commentVoteRepository;
//    private final PostVoteRepository postVoteRepository;
//    private final CommentMapper commentMapper;
//    private final CommentVoteMapper commentVoteMapper;
//
//    @PreAuthorize("@customSecurity.canViewComment(#commentId)")
//    public CommentVoteResponse vote(Long currentUserId, Long commentId, CommentVoteRequest commentVoteRequest) {
//
//        Optional<CommentVote> existingCommentVoteOpt = commentVoteRepository.findByComment_IdAndUser_Id(commentId, currentUserId);
//        CommentVoteType incomingCommentVoteType = commentVoteRequest.getCommentVoteType();
//
//        if (existingCommentVoteOpt.isPresent()) {
//
//            CommentVote existingCommentVote = existingCommentVoteOpt.get();
//            CommentVoteType currentCommentVoteType = existingCommentVote.getCommentVoteType();
//
//            if (incomingCommentVoteType.equals(currentCommentVoteType)) {
//                //Scenario 1: remove comment vote ( user click the same button)
//                commentVoteRepository.deleteByComment_IdAndUser_Id(commentId, user.getId());
//                if (incomingCommentVoteType == CommentVoteType.UPVOTE) {
//                    comment.setUpvoteCount(comment.getUpvoteCount() - 1);
//                } else if (incomingCommentVoteType == CommentVoteType.DOWNVOTE) {
//                    comment.setDownvoteCount(comment.getDownvoteCount() - 1);
//                }
//            } else {
//                //Scenario 2: switch comment vote
//                existingCommentVote.setCommentVoteType(commentVoteRequest.getCommentVoteType());
//                commentVoteRepository.save(existingCommentVote);
//
//                if (incomingCommentVoteType.equals(CommentVoteType.UPVOTE)) {
//                    comment.setUpvoteCount(comment.getUpvoteCount() + 1);
//                    comment.setDownvoteCount(comment.getDownvoteCount() - 1);
//                } else if (incomingCommentVoteType.equals(CommentVoteType.DOWNVOTE)) {
//                    comment.setDownvoteCount(comment.getDownvoteCount() + 1);
//                    comment.setUpvoteCount(comment.getUpvoteCount() - 1);
//                }
//            }
//        } else {
//            //Scenario 3: create comment vote
//            CommentVote commentVote = CommentVote.builder()
//                    .comment(comment)
//                    .commentVoteType(commentVoteRequest.getCommentVoteType())
//                    .user(user)
//                    .build();
//            commentVoteRepository.save(commentVote);
//
//            if (incomingCommentVoteType.equals(CommentVoteType.UPVOTE)) {
//                comment.setUpvoteCount(comment.getUpvoteCount() + 1);
//            } else if (incomingCommentVoteType.equals(CommentVoteType.DOWNVOTE)) {
//                comment.setDownvoteCount(comment.getDownvoteCount() + 1);
//
//            }
//        }
//
//        long score = comment.getUpvoteCount() - comment.getDownvoteCount();
//        comment.setVoteScore(score);
//        commentRepository.save(comment);
//
//        CommentVoteResponse commentVoteResponse = commentVoteMapper.toCommentVoteResponse(commentVoteRequest);
//
//        commentVoteResponse.setVoteScore(score);
//
//        commentVoteResponse.setCommentId(commentId);
//
//        return commentVoteResponse;
//    }
//}
