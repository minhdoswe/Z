package minhdoswe.socialnetwork.z.service;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.dto.request.PostRequest;
import minhdoswe.socialnetwork.z.entity.Post;
import minhdoswe.socialnetwork.z.entity.User;
import minhdoswe.socialnetwork.z.mapper.PostMapper;
import minhdoswe.socialnetwork.z.repository.PostRepository;
import minhdoswe.socialnetwork.z.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final SecurityUtils securityUtils;
    private final PostMapper postMapper;

    @Transactional
    public void createPost(PostRequest postRequest) {
        User user = securityUtils.getCurrentUser();
        Post post = postMapper.toPost(postRequest);
        post.setUser(user);
        postRepository.save(post);
    }
}
