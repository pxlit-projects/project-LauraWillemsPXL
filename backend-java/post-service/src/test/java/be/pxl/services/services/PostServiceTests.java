package be.pxl.services.services;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.PostStatus;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.exceptions.PermissionDeniedException;
import be.pxl.services.exceptions.ResourceNotFoundException;
import be.pxl.services.repository.IPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTests {
    private Post post;
    private Post post2;
    private PostRequest postRequest;

    @Mock
    private IPostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Captor
    private ArgumentCaptor<Post> postCaptor;

    @BeforeEach
    public void setUp() {
        post = Post.builder()
                .id(1L)
                .title("title")
                .content("content")
                .tags(List.of("tag1", "tag2"))
                .author("John Doe")
                .publishedDate(new Date(2025, 1, 1))
                .isDraft(false)
                .status(PostStatus.PENDING)
                .reviewNotifications(List.of("reviewNotification1", "reviewNotification2"))
                .build();

        post2 = Post.builder()
                .id(2L)
                .title("title 2")
                .content("content 2")
                .tags(List.of("tag1", "tag2"))
                .author("John Doe")
                .publishedDate(new Date(2025, 1, 1))
                .isDraft(false)
                .status(PostStatus.PENDING)
                .reviewNotifications(List.of("reviewNotification1", "reviewNotification2"))
                .build();

        postRequest = PostRequest.builder()
                .title("Title")
                .content("Content")
                .tags(List.of("tag1", "tag2"))
                .author("John Doe")
                .isDraft(false)
                .build();
    }

    @Test
    public void testAddPost() {
        postService.addPost(postRequest);

        Mockito.verify(postRepository).save(postCaptor.capture());
        Post capturedPost = postCaptor.getValue();

        assertEquals(postRequest.getTitle(), capturedPost.getTitle());
        assertEquals(postRequest.getContent(), capturedPost.getContent());
        assertEquals(postRequest.getTags(), capturedPost.getTags());
        assertEquals(postRequest.getAuthor(), capturedPost.getAuthor());
        assertEquals(postRequest.isDraft(), capturedPost.isDraft());
    }

    @Test
    public void testGetAllPosts() {
        when(postRepository.findAll()).thenReturn(List.of(post, post2));

        List<PostResponse> posts = postService.getAllPosts("editor", "John Doe");

        assertEquals(2, posts.size());
        assertEquals(post.getId(), posts.get(0).getId());
        assertEquals(post2.getId(), posts.get(1).getId());
        assertEquals(post.getTitle(), posts.get(0).getTitle());
        assertEquals(post2.getTitle(), posts.get(1).getTitle());
        assertEquals(post.getContent(), posts.get(0).getContent());
        assertEquals(post2.getContent(), posts.get(1).getContent());
        assertEquals(post.getTags(), posts.get(0).getTags());
        assertEquals(post2.getTags(), posts.get(1).getTags());
        assertEquals(post.getAuthor(), posts.get(0).getAuthor());
        assertEquals(post2.getAuthor(), posts.get(1).getAuthor());
        assertEquals(post.isDraft(), posts.get(0).isDraft());
        assertEquals(post2.isDraft(), posts.get(1).isDraft());
    }

    @Test
    public void testGetPostById() {
        when(postRepository.findById(post.getId())).thenReturn(java.util.Optional.of(post));

        PostResponse postResponse = postService.getPostById(post.getId());

        assertEquals(post.getId(), postResponse.getId());
        assertEquals(post.getTitle(), postResponse.getTitle());
        assertEquals(post.getContent(), postResponse.getContent());
        assertEquals(post.getTags(), postResponse.getTags());
        assertEquals(post.getAuthor(), postResponse.getAuthor());
        assertEquals(post.isDraft(), postResponse.isDraft());
    }

    @Test
    public void testGetPostById_WithInvalidId_ShouldThrowResourceNotFoundException() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.getPostById(post.getId()));
    }

    @Test
    public void testGetAllDrafts() {
        post.setDraft(true);
        post2.setDraft(true);
        when(postRepository.findAll()).thenReturn(List.of(post, post2));

        List<PostResponse> drafts = postService.getAllDrafts("editor", "John Doe");

        assertEquals(2, drafts.size());
        assertEquals(post.getId(), drafts.get(0).getId());
        assertEquals(post2.getId(), drafts.get(1).getId());
        assertEquals(post.getTitle(), drafts.get(0).getTitle());
        assertEquals(post2.getTitle(), drafts.get(1).getTitle());
        assertEquals(post.getContent(), drafts.get(0).getContent());
        assertEquals(post2.getContent(), drafts.get(1).getContent());
        assertEquals(post.getTags(), drafts.get(0).getTags());
        assertEquals(post2.getTags(), drafts.get(1).getTags());
        assertEquals(post.getAuthor(), drafts.get(0).getAuthor());
        assertEquals(post2.getAuthor(), drafts.get(1).getAuthor());
        assertEquals(post.isDraft(), drafts.get(0).isDraft());
        assertEquals(post2.isDraft(), drafts.get(1).isDraft());
        assertEquals(post.getPublishedDate(), drafts.get(0).getPublishedDate());
        assertEquals(post2.getPublishedDate(), drafts.get(1).getPublishedDate());
    }

    @Test
    public void testGetAllDrafts_WithInvalidPermissions_ShouldThrowPermissionDeniedException() {
        assertThrows(PermissionDeniedException.class, () -> postService.getAllDrafts("user", "John Doe"));
    }

    @Test
    public void testUpdatePost() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        postRequest.setTitle("Updated Title");

        PostResponse updatedPost = postService.updatePost(post.getId(), postRequest, "editor", "John Doe");

        assertEquals(post.getId(), updatedPost.getId());
        assertEquals("Updated Title", updatedPost.getTitle());
        assertEquals(postRequest.getContent(), updatedPost.getContent());
        assertEquals(postRequest.getTags(), updatedPost.getTags());
        assertEquals(postRequest.getAuthor(), updatedPost.getAuthor());
        assertNotNull(updatedPost.getPublishedDate());
        assertEquals(postRequest.isDraft(), updatedPost.isDraft());
    }

    @Test
    public void testUpdatePost_WithInvalidId_ShouldThrowResourceNotFoundException() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.updatePost(post.getId(), postRequest, "editor", "John Doe"));
    }

    @Test
    public void testUpdatePost_WithInvalidPermissions_ShouldThrowPermissionDeniedException() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        assertThrows(PermissionDeniedException.class, () -> postService.updatePost(post.getId(), postRequest, "user", "Jane Doe"));
    }

    @Test
    public void testDeleteDraft() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        postService.deleteDraft(post.getId(), "editor", "John Doe");

        verify(postRepository).delete(post);
    }

    @Test
    public void testDeleteDraft_WithInvalidId_ShouldThrowResourceNotFoundException() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.deleteDraft(post.getId(), "editor", "John Doe"));
    }

    @Test
    public void testDeleteDraft_WithInvalidPermission_ShouldThrowPermissionDeniedException() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        assertThrows(PermissionDeniedException.class, () -> postService.deleteDraft(post.getId(), "user", "Jane Doe"));
    }

    @Test
    public void testReviewPost() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        ReviewRequest reviewRequest = new ReviewRequest("approved");

        PostResponse reviewedPost = postService.reviewPost(post.getId(), reviewRequest);

        assertEquals(post.getId(), reviewedPost.getId());
        assertEquals(post.getTitle(), reviewedPost.getTitle());
        assertEquals(post.getContent(), reviewedPost.getContent());
        assertEquals(post.getTags(), reviewedPost.getTags());
        assertEquals(post.getAuthor(), reviewedPost.getAuthor());
        assertEquals(post.getPublishedDate(), reviewedPost.getPublishedDate());
        assertEquals(post.isDraft(), reviewedPost.isDraft());
        assertEquals(PostStatus.APPROVED, reviewedPost.getStatus());
    }

    @Test
    public void testReviewPost_WithInvalidId_ShouldThrowResourceNotFoundException() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.empty());

        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setReview("approved");

        assertThrows(ResourceNotFoundException.class, () -> postService.reviewPost(post.getId(), reviewRequest));
    }

    @Test
    public void testReviewPost_WithInvalidStatus_ShouldThrowIllegalArgumentException() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setReview("invalid");

        assertThrows(IllegalArgumentException.class, () -> postService.reviewPost(post.getId(), reviewRequest));
    }

    @Test
    public void testGetNotificationsOfAuthor() {
        when(postRepository.findAll()).thenReturn(List.of(post));

        List<String> notifications = postService.getNotificationsOfAuthor("editor", "John Doe");

        assertEquals(2, notifications.size());
        assertEquals("reviewNotification1", notifications.get(0));
        assertEquals("reviewNotification2", notifications.get(1));
    }

    @Test
    public void testGetNotificationsOfAuthor_WithInvalidPermissions_ShouldThrowPermissionDeniedException() {
        assertThrows(PermissionDeniedException.class, () -> postService.getNotificationsOfAuthor("user", "John Doe"));
    }

    @Test
    public void testGetNotificationsOfAuthor_WithNoPosts_ShouldThrowResourceNotFoundException() {
        when(postRepository.findAll()).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> postService.getNotificationsOfAuthor("editor", "John Doe"));
    }
}
