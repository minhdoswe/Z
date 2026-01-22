package minhdoswe.socialnetwork.z.modules.content.internal.helper;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.modules.content.internal.mapper.CommentMapper;
import minhdoswe.socialnetwork.z.modules.content.internal.mapper.PostMapper;
import minhdoswe.socialnetwork.z.modules.content.internal.model.dto.CommentResponse;
import minhdoswe.socialnetwork.z.modules.content.internal.model.dto.PostResponse;
import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Comment;
import minhdoswe.socialnetwork.z.modules.content.internal.model.entity.Post;
import minhdoswe.socialnetwork.z.modules.user.UserAPI;
import minhdoswe.socialnetwork.z.modules.user.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PostEnricher {

    private final UserAPI userAPI;
    private final PostMapper postMapper;

    public List<PostResponse> enrichList(List<Post> postList) {

        Set<Long> userIdSet = postList.stream()
                .map(Post::getUserId)
                .collect(Collectors.toSet());

        Map<Long, UserDTO> userDTOMap = userAPI.getUserDTO(userIdSet);

        return postList.stream().map(
                        post -> postMapper.toPostResponse(post)
                                .setUserDTO(userDTOMap.get(post.getUserId()))
                )
                .toList();
    }

    public PostResponse enrichOne(Post post) {

        UserDTO userDTO = userAPI.getUserDTO(post.getUserId());

        return postMapper.toPostResponse(post).setUserDTO(userDTO);
    }
}
