package be.pxl.services.services;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.exceptions.PermissionDeniedException;
import be.pxl.services.exceptions.ResourceNotFoundException;
import be.pxl.services.repository.IPostRepository;
import be.pxl.services.services.interfaces.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    private final IPostRepository postRepository;

    @Override
    public PostResponse addPost(PostRequest postRequest) {
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .tags(postRequest.getTags())
                .author(postRequest.getAuthor())
                .publishedDate(new Date())
                .isDraft(postRequest.isDraft())
                .build();

        postRepository.save(post);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .tags(post.getTags())
                .author(post.getAuthor())
                .publishedDate(post.getPublishedDate())
                .isDraft(post.isDraft())
                .build();
    }

    @Override
    public List<PostResponse> getAllPosts(String userRole, String userName) {
        if (userRole.equals("editor")) {
            List<Post> posts = postRepository.findAll().stream()
                    .filter(post -> post.getAuthor().equals(userName) && !post.isDraft())
                    .toList();

            return posts.stream().map(post -> PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .tags(post.getTags())
                    .author(post.getAuthor())
                    .publishedDate(post.getPublishedDate())
                    .isDraft(post.isDraft())
                    .build())
                    .toList();
        }

        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .tags(post.getTags())
                .author(post.getAuthor())
                .publishedDate(post.getPublishedDate())
                .isDraft(post.isDraft())
                .build())
                .toList();
    }

    @Override
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElse(null);

        if (post == null) {
            throw new ResourceNotFoundException("Post not found");
        }

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .tags(post.getTags())
                .author(post.getAuthor())
                .publishedDate(post.getPublishedDate())
                .isDraft(post.isDraft())
                .build();
    }

    @Override
    public List<PostResponse> getAllDrafts(String userRole, String userName) {
        if (userRole.equals("editor")) {
            List<Post> posts = postRepository.findAll().stream()
                    .filter(post -> post.getAuthor().equals(userName) && post.isDraft())
                    .toList();

            return posts.stream().map(post -> PostResponse.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .tags(post.getTags())
                            .author(post.getAuthor())
                            .publishedDate(post.getPublishedDate())
                            .isDraft(post.isDraft())
                            .build())
                    .toList();
        }
        else
        {
            throw new PermissionDeniedException("You are not allowed to view drafts");
        }
    }

    @Override
    public PostResponse updateDraft(Long id, PostRequest postRequest, String userRole, String userName) {
        Post post = postRepository.findById(id).orElse(null);

        if (post == null) {
            throw new ResourceNotFoundException("Post not found");
        }

        if (!post.getAuthor().equals(userName) && !userRole.equals("editor")) {
            throw new PermissionDeniedException("You are not allowed to update this post");
        }

        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setTags(postRequest.getTags());
        post.setAuthor(postRequest.getAuthor());
        post.setPublishedDate(new Date());
        post.setDraft(postRequest.isDraft());

        postRepository.save(post);

        return PostResponse.builder()
                 .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .tags(post.getTags())
                .author(post.getAuthor())
                .publishedDate(post.getPublishedDate())
                .isDraft(post.isDraft())
                .build();
    }

    @Override
    public void deleteDraft(Long id, String userRole, String userName) {
        Post post = postRepository.findById(id).orElse(null);

        if (post == null) {
            throw new ResourceNotFoundException("Post not found");
        }

        if (!post.getAuthor().equals(userName) && !userRole.equals("editor")) {
            throw new PermissionDeniedException("You are not allowed to delete this post");
        }

        postRepository.delete(post);
    }
}
