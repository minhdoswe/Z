//package minhdoswe.socialnetwork.z.modules.content.internal.controller;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import minhdoswe.socialnetwork.z.common.annotation.CurrentUserId;
//import minhdoswe.socialnetwork.z.modules.content.internal.model.dto.PostRequest;
//import minhdoswe.socialnetwork.z.modules.content.internal.model.dto.PostResponse;
//import minhdoswe.socialnetwork.z.modules.content.internal.service.PostService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api")
//public class PostController {
//
//    private final PostService postService;
//
//    @PostMapping("/posts")
//    public ResponseEntity<PostResponse> create(@CurrentUserId Long userId, @Valid @RequestBody PostRequest postRequest) {
//        PostResponse postResponse = postService.create(userId, postRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
//    }
//
//    @GetMapping("/posts/{postId}")
//    public ResponseEntity<PostResponse> get(@PathVariable Long postId) {
//
//        PostResponse postResponse = postService.getById(postId);
//
//        return ResponseEntity.ok(postResponse);
//    }
//
//    @DeleteMapping("/posts/{postId}")
//    public ResponseEntity<Void> delete(@CurrentUserId Long currentUserId, @PathVariable Long postId) {
//        postService.delete(currentUserId, postId);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PutMapping("/posts/{postId}")
//    public ResponseEntity<String> modify(@CurrentUserId Long currentUserId, @PathVariable Long postId, @RequestBody PostRequest postRequest) {
//        postService.modify(currentUserId, postId, postRequest);
//        return ResponseEntity.ok("Post modified successfully");
//    }
//
//    @GetMapping("/users/{userId}/posts")
//    public ResponseEntity<List<PostResponse>> findByUserId(@CurrentUserId Long currentUserId, @PathVariable Long userId) {
//        List<PostResponse> postResponseDTOList = postService.findByUserId(currentUserId, userId);
//
//        return ResponseEntity.ok(postResponseDTOList);
//    }
//
//
//}
