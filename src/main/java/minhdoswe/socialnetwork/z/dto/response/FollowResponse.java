package minhdoswe.socialnetwork.z.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Setter
@Getter
public class FollowResponse {

    private boolean isFollowing;
    private boolean isFriend;
}
