package minhdoswe.socialnetwork.z.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minhdoswe.socialnetwork.z.dto.request.VoteRequest;
import minhdoswe.socialnetwork.z.dto.response.VoteDTO;
import minhdoswe.socialnetwork.z.dto.response.VoteResponse;
import minhdoswe.socialnetwork.z.entity.Post;
import minhdoswe.socialnetwork.z.entity.User;
import minhdoswe.socialnetwork.z.entity.Vote;
import minhdoswe.socialnetwork.z.enums.Visibility;
import minhdoswe.socialnetwork.z.enums.VoteStatus;
import minhdoswe.socialnetwork.z.exception.post.PostNotFoundException;
import minhdoswe.socialnetwork.z.mapper.VoteMapper;
import minhdoswe.socialnetwork.z.repository.PostRepository;
import minhdoswe.socialnetwork.z.repository.VoteRepository;
import minhdoswe.socialnetwork.z.security.expression.CustomSecurityExpression;
import minhdoswe.socialnetwork.z.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class VoteService {

    private final PostRepository postRepository;
    private final VoteRepository voteRepository;
    private final SecurityUtils securityUtils;
    private final VoteMapper voteMapper;

    @Transactional
    @PreAuthorize("@customSecurity.canViewPost(#postId)")
    public VoteResponse vote(Long postId, VoteRequest voteRequest) {

        VoteContext voteContext = getVoteContext(postId);
        Post post = voteContext.post;
        User user = voteContext.user;

        Optional<Vote> existingVoteOpt = voteRepository.findVoteByPostAndUser(post, user);

        if (existingVoteOpt.isPresent()) {
            Vote existingVote = existingVoteOpt.get();

            if (existingVote.getVoteStatus() == voteRequest.getVoteStatus()) {
                //if they click the same button they click before, they want to delete their vote
                voteRepository.deleteVoteByPostAndUser(post, user);
            } else {
                //if they clock the opposite vote, they change their mind, modify current vote
                existingVote.setVoteStatus(voteRequest.getVoteStatus());
                voteRepository.save(existingVote);
            }
        } else {
            //they haven't voted yet, create vote
            Vote vote = Vote.builder()
                    .voteStatus(voteRequest.getVoteStatus())
                    .post(post)
                    .user(user)
                    .build();

            voteRepository.save(vote);
        }

        //update the upvote, downvote count to post repository to make user easier to retrieve the count
        long upvoteCount = voteRepository.countVotesByPostAndVoteStatus(post, VoteStatus.UPVOTE);
        long downvoteCount = voteRepository.countVotesByPostAndVoteStatus(post, VoteStatus.DOWNVOTE);
        post.setUpvoteCount(upvoteCount);
        post.setDownvoteCount(downvoteCount);
        post.setVoteScore(upvoteCount - downvoteCount);

        postRepository.save(post);

        //build the response
        VoteResponse voteResponse = voteMapper.toVoteResponse(voteRequest);
        voteResponse.setPostId(postId);

        return voteResponse;
    }

    private record VoteContext(Post post, User user) {}

    public VoteContext getVoteContext(Long postId) {

        User user = securityUtils.getCurrentUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("post not found"));

        return new VoteContext(post, user);
    }

    @PreAuthorize("@customSecurity.canViewPost(#postId)")
    public List<VoteDTO> getVotesByPost(Long postId, VoteRequest voteRequest) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("post not found"));
        return voteRepository.findVotesByPostAndVoteStatus(post, voteRequest.getVoteStatus())
                .stream().map(voteMapper::toVoteDTO)
                .toList();
    }

    public List<VoteDTO> getVotesByUser(Long targetUserId) {

        Long currentUserId = securityUtils.getCurrentUser().getId();
        return voteRepository.findVisibleVotesByTargetUser(currentUserId, targetUserId)
                .stream().map(voteMapper::toVoteDTO)
                .toList();
    }
}
