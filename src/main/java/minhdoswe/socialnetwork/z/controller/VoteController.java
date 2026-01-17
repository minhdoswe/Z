package minhdoswe.socialnetwork.z.controller;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.dto.request.VoteRequest;
import minhdoswe.socialnetwork.z.dto.response.VoteDTO;
import minhdoswe.socialnetwork.z.dto.response.VoteResponse;
import minhdoswe.socialnetwork.z.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vote")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/{postId}")
    public ResponseEntity<VoteResponse> vote(@PathVariable Long postId, @RequestBody VoteRequest voteRequest) {

        VoteResponse voteResponse = voteService.vote(postId, voteRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(voteResponse);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<VoteDTO>> getVotesByPost(@PathVariable Long postId, @RequestBody VoteRequest voteRequest) {

        List<VoteDTO> voteDTOList = voteService.getVotesByPost(postId, voteRequest);

        return ResponseEntity.ok(voteDTOList);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VoteDTO>> getVotesByUser(@PathVariable Long userId) {

        List<VoteDTO> voteDTOList = voteService.getVotesByUser(userId);

        return ResponseEntity.ok(voteDTOList);
    }
}
