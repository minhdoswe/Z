package minhdoswe.socialnetwork.z.modules.relationship.model.dto;

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
