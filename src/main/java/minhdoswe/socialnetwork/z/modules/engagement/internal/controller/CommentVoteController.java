//package minhdoswe.socialnetwork.z.modules.engagement.internal.controller;
//
//import lombok.RequiredArgsConstructor;
//import minhdoswe.socialnetwork.z.modules.engagement.internal.model.dto.CommentVoteRequest;
//import minhdoswe.socialnetwork.z.modules.engagement.internal.model.dto.CommentVoteResponse;
//import minhdoswe.socialnetwork.z.modules.engagement.internal.service.CommentVoteService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RequestMapping("/api/comments")
//@RestController
//@RequiredArgsConstructor
//public class CommentVoteController {
//
//    private final CommentVoteService commentVoteService;
//
//    @PostMapping("/{commentId}/vote")
//    public ResponseEntity<CommentVoteResponse> create(@PathVariable Long commentId, @RequestBody CommentVoteRequest commentVoteRequest) {
//
//        CommentVoteResponse commentVoteResponse = commentVoteService.vote(commentId, commentVoteRequest);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(commentVoteResponse);
//    }
//}
