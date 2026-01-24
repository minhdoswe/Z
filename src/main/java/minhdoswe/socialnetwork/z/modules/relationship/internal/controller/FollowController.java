package minhdoswe.socialnetwork.z.modules.relationship.internal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minhdoswe.socialnetwork.z.common.annotation.CurrentUserId;
import minhdoswe.socialnetwork.z.modules.relationship.internal.model.dto.FollowResponse;
import minhdoswe.socialnetwork.z.modules.relationship.internal.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class FollowController {

    private final FollowService followService;

    @PostMapping("/users/{targetId}/follow")
    public ResponseEntity<FollowResponse> follow(@CurrentUserId Long currentUserId, @PathVariable Long targetId) {

        FollowResponse followResponse = followService.follow(currentUserId, targetId);

        return ResponseEntity.status(HttpStatus.CREATED).body(followResponse);
    }

    @DeleteMapping("/users/{targetId}/follow")
    public ResponseEntity<Void> unfollow(@CurrentUserId Long currentUserId, @PathVariable Long targetId) {

        followService.unfollow(currentUserId, targetId); // Assuming this returns void or response

        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/user/{targetId}/followers")
//    public ResponseEntity<List<UserDTO>> getFollowers(@PathVariable Long targetId) {
//
//        List<UserDTO> userDTOList = followService.getFollowers(targetId);
//
//        return ResponseEntity.ok(userDTOList);
//    }

//    @DeleteMapping("/user/followers/{followerId}")
//    public ResponseEntity<Void> deleteFollower(@PathVariable Long followerId) {
//
//        followService.deleteFollower(followerId);
//
//        return ResponseEntity.noContent().build();
//    }
}
