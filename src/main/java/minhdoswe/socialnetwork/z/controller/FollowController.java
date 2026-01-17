package minhdoswe.socialnetwork.z.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minhdoswe.socialnetwork.z.dto.request.follow.FollowRequest;
import minhdoswe.socialnetwork.z.dto.response.follow.FollowResponse;
import minhdoswe.socialnetwork.z.service.FollowService;
import minhdoswe.socialnetwork.z.util.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
@Slf4j
public class FollowController {

    private final SecurityUtils securityUtils;
    private final FollowService followService;

    @PostMapping("/{targetId}")
    public ResponseEntity<FollowResponse> follow(@PathVariable Long targetId) {

        Long followerId = securityUtils.getCurrentUser().getId();

        FollowResponse followResponse = followService.follow(followerId, targetId);

        return ResponseEntity.status(HttpStatus.CREATED).body(followResponse);
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<String> unfollow(@PathVariable Long targetId) {
        log.info("reached unfollow controller for target: " + targetId);
        Long followerId = securityUtils.getCurrentUser().getId();

        followService.unfollow(followerId, targetId); // Assuming this returns void or response

        return ResponseEntity.ok("Unfollowed successfully");
    }
}
