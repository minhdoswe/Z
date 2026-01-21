package minhdoswe.socialnetwork.z.modules.engagement.internal.controller;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.engagement.internal.model.dto.PostVoteRequest;
import minhdoswe.socialnetwork.z.modules.engagement.internal.model.dto.PostVoteResponse;
import minhdoswe.socialnetwork.z.modules.engagement.internal.model.dto.VoteDTO;
import minhdoswe.socialnetwork.z.modules.engagement.internal.service.PostVoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vote")
@RequiredArgsConstructor
public class PostVoteController {

    private final PostVoteService postVoteService;

    @PostMapping("/{postId}")
    public ResponseEntity<PostVoteResponse> vote(@PathVariable Long postId, @RequestBody PostVoteRequest postVoteRequest) {

        PostVoteResponse voteResponse = postVoteService.vote(postId, postVoteRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(voteResponse);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<VoteDTO>> getVotesByPost(@PathVariable Long postId, @RequestBody PostVoteRequest postVoteRequest) {

        List<VoteDTO> voteDTOList = postVoteService.getVotesByPost(postId, postVoteRequest);

        return ResponseEntity.ok(voteDTOList);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VoteDTO>> getVotesByUser(@PathVariable Long userId) {

        List<VoteDTO> voteDTOList = postVoteService.getVotesByUser(userId);

        return ResponseEntity.ok(voteDTOList);
    }
}
