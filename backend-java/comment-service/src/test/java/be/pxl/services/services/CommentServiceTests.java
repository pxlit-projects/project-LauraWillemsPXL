package be.pxl.services.services;

import be.pxl.services.client.PostClient;
import be.pxl.services.domain.Comment;
import be.pxl.services.domain.PostResponse;
import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.exceptions.PermissionDeniedException;
import be.pxl.services.exceptions.ResourceNotFoundException;
import be.pxl.services.repository.ICommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTests {
    private Comment comment;
    private CommentRequest commentRequest;

    @Mock
    private ICommentRepository commentRepository;

    @Mock
    private PostClient postClient;

    @InjectMocks
    private CommentService commentService;

    @Captor
    private ArgumentCaptor<Comment> commentCaptor;

    @BeforeEach
    public void setUp() {
        comment = Comment.builder()
                .id(1L)
                .postId(1L)
                .comment("This is a comment")
                .createdBy("John Doe")
                .createdAt(new Date())
                .build();

        commentRequest = CommentRequest.builder()
                .postId(1L)
                .comment("This is a comment")
                .createdBy("John Doe")
                .build();
    }

    @Test
    public void testAddComment() {
        PostResponse postResponse = PostResponse.builder().id(1L).build();
        when(postClient.getPostById(commentRequest.getPostId())).thenReturn(postResponse);

        CommentResponse commentResponse = commentService.addComment(commentRequest);

        verify(commentRepository).save(commentCaptor.capture());
        Comment capturedComment = commentCaptor.getValue();

        assertEquals(commentRequest.getPostId(), capturedComment.getPostId());
        assertEquals(commentRequest.getComment(), capturedComment.getComment());
        assertEquals(commentRequest.getCreatedBy(), capturedComment.getCreatedBy());
        assertNotNull(capturedComment.getCreatedAt());

        assertEquals(comment.getPostId(), commentResponse.getPostId());
        assertEquals(comment.getComment(), commentResponse.getComment());
        assertEquals(comment.getCreatedBy(), commentResponse.getCreatedBy());
        assertNotNull(commentResponse.getCreatedAt());
    }

    @Test
    public void testAddComment_WithNonExistingPost_ShouldThrowResourceNotFoundException() {
        when(postClient.getPostById(commentRequest.getPostId())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> commentService.addComment(commentRequest));
    }

    @Test
    public void testGetAllComments() {
        when(commentRepository.findAll()).thenReturn(List.of(comment));

        List<CommentResponse> comments = commentService.getAllComments();

        assertEquals(1, comments.size());
        assertEquals(comment.getId(), comments.get(0).getId());
        assertEquals(comment.getPostId(), comments.get(0).getPostId());
        assertEquals(comment.getComment(), comments.get(0).getComment());
        assertEquals(comment.getCreatedBy(), comments.get(0).getCreatedBy());
        assertEquals(comment.getCreatedAt(), comments.get(0).getCreatedAt());
    }

    @Test
    public void testUpdateComment() {
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        commentRequest.setComment("Updated comment");

        CommentResponse updatedComment = commentService.updateComment(comment.getId(), commentRequest, "user", "John Doe");

        assertEquals(comment.getId(), updatedComment.getId());
        assertEquals(comment.getPostId(), updatedComment.getPostId());
        assertEquals("Updated comment", updatedComment.getComment());
        assertEquals(comment.getCreatedBy(), updatedComment.getCreatedBy());
        assertEquals(comment.getCreatedAt(), updatedComment.getCreatedAt());
    }

    @Test
    public void testUpdateComment_WithInvalidId_ShouldThrowResourceNotFoundException() {
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commentService.updateComment(comment.getId(), commentRequest, "user", "John Doe"));
    }

    @Test
    public void testUpdateComment_WithInvalidPermissions_ShouldThrowPermissionDeniedException() {
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        assertThrows(PermissionDeniedException.class, () -> commentService.updateComment(comment.getId(), commentRequest, "editor", "Jane Doe"));
    }

    @Test
    public void testDeleteComment() {
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        commentService.deleteComment(comment.getId(), "user", "John Doe");

        verify(commentRepository).delete(comment);
    }

    @Test
    public void testDeleteComment_WithInvalidId_ShouldThrowResourceNotFoundException() {
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commentService.deleteComment(comment.getId(), "user", "John Doe"));
    }

    @Test
    public void testDeleteComment_WithInvalidPermissions_ShouldThrowPermissionDeniedException() {
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        assertThrows(PermissionDeniedException.class, () -> commentService.deleteComment(comment.getId(), "editor", "Jane Doe"));
    }
}