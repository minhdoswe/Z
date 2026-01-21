package minhdoswe.socialnetwork.z.modules.engagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minhdoswe.socialnetwork.z.modules.engagement.model.dto.PostVoteRequest;
import minhdoswe.socialnetwork.z.modules.engagement.model.dto.PostVoteResponse;
import minhdoswe.socialnetwork.z.modules.engagement.model.dto.VoteDTO;
import minhdoswe.socialnetwork.z.modules.content.model.entity.Post;
import minhdoswe.socialnetwork.z.modules.engagement.model.entity.PostVote;
import minhdoswe.socialnetwork.z.modules.user.model.entity.User;
import minhdoswe.socialnetwork.z.modules.engagement.enums.PostVoteType;
import minhdoswe.socialnetwork.z.modules.content.exception.post.post.PostNotFoundException;
import minhdoswe.socialnetwork.z.modules.engagement.mapper.PostVoteMapper;
import minhdoswe.socialnetwork.z.modules.content.repository.PostRepository;
import minhdoswe.socialnetwork.z.modules.engagement.repository.PostVoteRepository;
import minhdoswe.socialnetwork.z.common.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostVoteService {

    private final PostRepository postRepository;
    private final PostVoteRepository postVoteRepository;
    private final SecurityUtils securityUtils;
    private final PostVoteMapper postVoteMapper;

    @Transactional
    @PreAuthorize("@customSecurity.canViewPost(#postId)")
    public PostVoteResponse vote(Long postId, PostVoteRequest postVoteRequest) {

        VoteContext voteContext = getVoteContext(postId);
        Post post = voteContext.post;
        User user = voteContext.user;

        Optional<PostVote> existingVoteOpt = postVoteRepository.findByPostAndUser(post, user);
        PostVoteType incomingVoteType = postVoteRequest.getPostVoteType();

        if (existingVoteOpt.isPresent()) {
            PostVote existingPostVote = existingVoteOpt.get();
            PostVoteType currentVoteType = existingPostVote.getPostVoteType();
            //Scenario 1: remove vote (user click the same button)
            if (existingPostVote.getPostVoteType() == postVoteRequest.getPostVoteType()) {
                //if they click the same button they click before, they want to delete their vote
                postVoteRepository.deleteByPostAndUser(post, user);
                if (incomingVoteType == PostVoteType.DOWNVOTE.UPVOTE) {
                    post.setUpvoteCount(post.getUpvoteCount() - 1);
                } else if (incomingVoteType == PostVoteType.DOWNVOTE) {
                    post.setDownvoteCount(post.getDownvoteCount() - 1);
                }
            } else {
                //if they clock the opposite vote, they change their mind, modify current vote
                existingPostVote.setPostVoteType(postVoteRequest.getPostVoteType());
                postVoteRepository.save(existingPostVote);

                if (incomingVoteType == PostVoteType.UPVOTE) {
                    post.setDownvoteCount(post.getDownvoteCount() - 1);
                    post.setUpvoteCount(post.getUpvoteCount() + 1);
                } else {
                    post.setUpvoteCount(post.getUpvoteCount() - 1);
                    post.setDownvoteCount(post.getDownvoteCount() + 1);
                }
            }
        } else {
            //they haven't voted yet, create vote
            PostVote postVote = PostVote.builder()
                    .postVoteType(postVoteRequest.getPostVoteType())
                    .post(post)
                    .user(user)
                    .build();

            postVoteRepository.save(postVote);
            if (incomingVoteType == PostVoteType.UPVOTE) {
                post.setUpvoteCount(post.getUpvoteCount() + 1);
            } else {
                post.setDownvoteCount(post.getDownvoteCount() + 1);
            }
        }

        long score = post.getUpvoteCount() - post.getDownvoteCount();
        post.setVoteScore(score);
        postRepository.save(post);

        //build the response
        PostVoteResponse postVoteResponse = postVoteMapper.toPostVoteResponse(postVoteRequest);
        postVoteResponse.setVoteScore(score);
        postVoteResponse.setPostId(postId);

        return postVoteResponse;
    }

    private record VoteContext(Post post, User user) {}

    public VoteContext getVoteContext(Long postId) {

        User user = securityUtils.getCurrentUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("post not found"));

        return new VoteContext(post, user);
    }

    @PreAuthorize("@customSecurity.canViewPost(#postId)")
    public List<VoteDTO> getVotesByPost(Long postId, PostVoteRequest postVoteRequest) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("post not found"));
        return postVoteRepository.findByPostAndPostVoteType(post, postVoteRequest.getPostVoteType())
                .stream().map(postVoteMapper::toVoteDTO)
                .toList();
    }

    public List<VoteDTO> getVotesByUser(Long targetUserId) {

        Long currentUserId = securityUtils.getCurrentUser().getId();
        return postVoteRepository.findVisibleVotesByTargetUser(currentUserId, targetUserId)
                .stream().map(postVoteMapper::toVoteDTO)
                .toList();
    }
}
