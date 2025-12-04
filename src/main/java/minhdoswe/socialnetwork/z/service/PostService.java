package minhdoswe.socialnetwork.z.service;

import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.dto.request.PostRequest;
import minhdoswe.socialnetwork.z.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void createPost(PostRequest postRequest) {

    }
}
