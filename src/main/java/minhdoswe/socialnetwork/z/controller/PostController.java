package minhdoswe.socialnetwork.z.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minhdoswe.socialnetwork.z.dto.request.post.PostRequest;
import minhdoswe.socialnetwork.z.dto.response.post.PostResponseDTO;
import minhdoswe.socialnetwork.z.entity.Post;
import minhdoswe.socialnetwork.z.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@Valid @RequestBody PostRequest postRequest) {
        postService.createPost(postRequest);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @PutMapping("/{postId}")
    public ResponseEntity<String> modifyPost(@PathVariable Long postId, @RequestBody PostRequest postRequest) {
        postService.modifyPost(postId, postRequest);
        return ResponseEntity.ok("Post modified successfully");
    }

    @GetMapping("/user/{targetId}")
    public ResponseEntity<List<PostResponseDTO>> findPostsByTargetId(@PathVariable Long targetId) {
        List<PostResponseDTO> postResponseDTOList = postService.findPostByUserId(targetId);

        return ResponseEntity.ok(postResponseDTOList);
    }


}
