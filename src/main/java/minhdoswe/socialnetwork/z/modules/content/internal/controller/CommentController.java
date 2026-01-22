package minhdoswe.socialnetwork.z.modules.content.internal.controller;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.common.annotation.CurrentUserId;
import minhdoswe.socialnetwork.z.modules.content.internal.model.dto.CommentRequest;
import minhdoswe.socialnetwork.z.modules.content.internal.model.dto.CommentResponse;
import minhdoswe.socialnetwork.z.modules.content.internal.mapper.CommentMapper;
import minhdoswe.socialnetwork.z.modules.content.internal.service.CommentService;
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
    public ResponseEntity<CommentResponse> create(@CurrentUserId Long userId, @PathVariable Long postId, @RequestBody CommentRequest commentRequest) {

        CommentResponse commentResponse = commentService.create(userId, postId, commentRequest);

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
    public ResponseEntity<CommentResponse> reply(@CurrentUserId Long userId, @PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {

        CommentResponse commentResponse = commentService.reply(userId, commentId, commentRequest);

        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("/comments/{commentId}/replies")
    public ResponseEntity<List<CommentResponse>> getByComment(@PathVariable Long commentId) {

        List<CommentResponse> commentResponseList = commentService.getByComment(commentId);

        return ResponseEntity.ok(commentResponseList);
    }

    @GetMapping("/users/{userId}/comments")
    public ResponseEntity<List<CommentResponse>> getByUser(@CurrentUserId Long currentUserId, @PathVariable Long userId) {

        List<CommentResponse> commentResponseList = commentService.getByUser(currentUserId, userId);

        return ResponseEntity.ok(commentResponseList);
    }
}
