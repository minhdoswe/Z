package minhdoswe.socialnetwork.z.controller;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.dto.response.FollowResponse;
import minhdoswe.socialnetwork.z.service.FollowService;
import minhdoswe.socialnetwork.z.util.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
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
    public ResponseEntity<Void> unfollow(@PathVariable Long targetId) {

        Long followerId = securityUtils.getCurrentUser().getId();

        FollowResponse followResponse = followService.unfollow(followerId, targetId);

        return ResponseEntity.noContent().build();
    }
}
