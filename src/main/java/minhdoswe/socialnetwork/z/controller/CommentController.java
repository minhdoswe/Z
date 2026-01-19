package minhdoswe.socialnetwork.z.controller;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.dto.request.CommentRequest;
import minhdoswe.socialnetwork.z.dto.response.CommentResponse;
import minhdoswe.socialnetwork.z.mapper.CommentMapper;
import minhdoswe.socialnetwork.z.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> create(@PathVariable Long postId, @RequestBody CommentRequest commentRequest) {

        CommentResponse commentResponse = commentService.create(postId, commentRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getByPost(@PathVariable Long postId) {

        List<CommentResponse> commentResponseList = commentService.getByPost(postId);

        return ResponseEntity.ok(commentResponseList);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> modify(@PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {

        CommentResponse commentResponse = commentService.modify(commentId, commentRequest);

        return ResponseEntity.ok(commentResponse);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId) {

        commentService.delete(commentId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comments/{commentId}/replies")
    public ResponseEntity<CommentResponse> reply(@PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {

        CommentResponse commentResponse = commentService.reply(commentId, commentRequest);

        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("/comments/{commentId}/replies")
    public ResponseEntity<List<CommentResponse>> getByComment(@PathVariable Long commentId) {

        List<CommentResponse> commentResponseList = commentService.getByComment(commentId);

        return ResponseEntity.ok(commentResponseList);
    }

    @GetMapping("/users/{userId}/comments")
    public ResponseEntity<List<CommentResponse>> getByUser(@PathVariable Long userId) {

        List<CommentResponse> commentResponseList = commentService.getByUser(userId);

        return ResponseEntity.ok(commentResponseList);
    }
}
