package minhdoswe.socialnetwork.z.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minhdoswe.socialnetwork.z.dto.response.FollowResponse;
import minhdoswe.socialnetwork.z.dto.response.UserDTO;
import minhdoswe.socialnetwork.z.service.FollowService;
import minhdoswe.socialnetwork.z.util.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class FollowController {

    private final SecurityUtils securityUtils;
    private final FollowService followService;

    @PostMapping("/users/{targetId}/follow")
    public ResponseEntity<FollowResponse> follow(@PathVariable Long targetId) {

        Long followerId = securityUtils.getCurrentUser().getId();

        FollowResponse followResponse = followService.follow(followerId, targetId);

        return ResponseEntity.status(HttpStatus.CREATED).body(followResponse);
    }

    @DeleteMapping("/users/{targetId}/follow")
    public ResponseEntity<Void> unfollow(@PathVariable Long targetId) {
        log.info("reached unfollow controller for target: " + targetId);
        Long followerId = securityUtils.getCurrentUser().getId();

        followService.unfollow(followerId, targetId); // Assuming this returns void or response

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{targetId}/followers")
    public ResponseEntity<List<UserDTO>> getFollowers(@PathVariable Long targetId) {

        List<UserDTO> userDTOList = followService.getFollowers(targetId);

        return ResponseEntity.ok(userDTOList);
    }

    @DeleteMapping("/user/followers/{followerId}")
    public ResponseEntity<Void> deleteFollower(@PathVariable Long followerId) {

        followService.deleteFollower(followerId);

        return ResponseEntity.noContent().build();
    }
}
