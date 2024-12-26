package be.pxl.services.services;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.repository.IPostRepository;
import be.pxl.services.services.interfaces.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    private final IPostRepository postRepository;

    @Override
    public PostResponse addPost(PostRequest postRequest) {
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .publishedDate(LocalDate.now())
                .build();

        postRepository.save(post);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .publishedDate(post.getPublishedDate())
                .build();
    }
}
