package be.pxl.services.services;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.PostStatus;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.exceptions.PermissionDeniedException;
import be.pxl.services.exceptions.ResourceNotFoundException;
import be.pxl.services.repository.IPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
                .status(PostStatus.PENDING)
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
            List<Post> posts = postRepository.findAll()
                    .stream()
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
                    .status(post.getStatus())
                    .build())
                    .toList();
        }

        if (userRole.equals("editor-in-chief")) {
            List<Post> posts = postRepository.findAll()
                    .stream()
                    .filter(post -> post.getStatus().equals(PostStatus.PENDING) && !post.isDraft())
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

        List<Post> posts = postRepository.findAll()
                .stream()
                .filter(post -> !post.isDraft() && post.getStatus().equals(PostStatus.APPROVED))
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
                .status(post.getStatus())
                .rejectionComment(post.getRejectionComment())
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
    public PostResponse updatePost(Long id, PostRequest postRequest, String userRole, String userName) {
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

        if (!post.isDraft()) {
            post.setStatus(PostStatus.PENDING);
        }

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

    @Override
    public PostResponse reviewPost(Long id, ReviewRequest reviewRequest) {
        Post post = postRepository.findById(id).orElse(null);

        if (post == null) {
            throw new ResourceNotFoundException("Post not found");
        }

        if (reviewRequest.getReview().equalsIgnoreCase("approved")) {
            post.setStatus(PostStatus.APPROVED);
        }
        else if (reviewRequest.getReview().equalsIgnoreCase("rejected")) {
            post.setStatus(PostStatus.REJECTED);
            post.setRejectionComment(reviewRequest.getRejectionComment());
        }
        else {
            throw new IllegalArgumentException("Invalid review status");
        }

        postRepository.save(post);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .tags(post.getTags())
                .author(post.getAuthor())
                .publishedDate(post.getPublishedDate())
                .status(post.getStatus())
                .rejectionComment(post.getRejectionComment())
                .isDraft(post.isDraft())
                .build();
    }

    @Override
    public List<String> getNotificationsOfAuthor(String userRole, String userName) {
        if (!userRole.equals("editor")) {
            throw new PermissionDeniedException("You are not allowed to view notifications");
        }

        List<Post> posts = postRepository.findAll()
                .stream()
                .filter(post -> post.getAuthor().equals(userName))
                .toList();

        if (posts.isEmpty()) {
            throw new ResourceNotFoundException("No posts found");
        }

        return posts.stream()
                .flatMap(post -> post.getReviewNotifications().stream())
                .toList();
    }

    @RabbitListener(queues = "notificationQueue")
    public void listen(String message) {
        System.out.println("Message read from myQueue: " + message);
        String[] messageParts = message.split(" ");
        Long postId = Long.parseLong(messageParts[3]);

        Post post = postRepository.findById(postId).orElse(null);

        if (post == null) {
            throw new ResourceNotFoundException("Post not found");
        }

        System.out.println("Post: " + postId);
        post.getReviewNotifications().add(message);
        postRepository.save(post);
    }
}
