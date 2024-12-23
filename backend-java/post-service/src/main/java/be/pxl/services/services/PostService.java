package be.pxl.services.services;

import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.repository.IPostRepository;
import be.pxl.services.services.interfaces.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    //private final IPostRepository postRepository;

    @Override
    public PostResponse addPost(PostRequest postRequest) {
        return null;
    }
}
