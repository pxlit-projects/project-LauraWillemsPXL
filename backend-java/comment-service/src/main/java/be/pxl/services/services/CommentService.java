package be.pxl.services.services;

import be.pxl.services.client.PostClient;
import be.pxl.services.domain.Comment;
import be.pxl.services.domain.PostResponse;
import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.exceptions.PermissionDeniedException;
import be.pxl.services.exceptions.ResourceNotFoundException;
import be.pxl.services.repository.ICommentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {
    private final ICommentRepository commentRepository;
    private final PostClient postClient;
    private final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Override
    public CommentResponse addComment(CommentRequest commentRequest) {
        PostResponse postResponse = postClient.getPostById(commentRequest.getPostId());

        if (postResponse == null) {
            logger.debug("The post for the created comment does not exist");
            throw new ResourceNotFoundException("The post for the created comment does not exist");
        }

        Comment comment = Comment.builder()
                .postId(commentRequest.getPostId())
                .comment(commentRequest.getComment())
                .createdBy(commentRequest.getCreatedBy())
                .createdAt(new Date())
                .build();

        logger.debug("Adding a new comment with details: {}", comment);

        commentRepository.save(comment);

        return CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .comment(comment.getComment())
                .createdBy(comment.getCreatedBy())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    @Override
    public List<CommentResponse> getAllComments() {
        List<Comment> comments = commentRepository.findAll();

        logger.debug("Retrieving all comments");

        return comments.stream().map(comment -> CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .comment(comment.getComment())
                .createdBy(comment.getCreatedBy())
                .createdAt(comment.getCreatedAt())
                .build())
                .toList();
    }

    @Override
    public CommentResponse updateComment(Long id, CommentRequest commentRequest, String userRole, String userName) {
        Comment comment = commentRepository.findById(id).orElse(null);

        if (comment == null) {
            logger.debug("Comment with id {} not found", id);
            throw new ResourceNotFoundException("Comment not found");
        }

        if (!comment.getCreatedBy().equals(userName) && !userRole.equals("user")) {
            logger.debug("User {} is not allowed to update this comment", userName);
            throw new PermissionDeniedException("You are not allowed to update this comment");
        }

        comment.setComment(commentRequest.getComment());

        commentRepository.save(comment);

        logger.debug("Updating comment with id {} to: {}", id, commentRequest);

        return CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .comment(comment.getComment())
                .createdBy(comment.getCreatedBy())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    @Override
    public void deleteComment(Long id, String userRole, String userName) {
        Comment comment = commentRepository.findById(id).orElse(null);

        if (comment == null) {
            logger.debug("Comment with id {} not found", id);
            throw new ResourceNotFoundException("Comment not found");
        }

        if (!comment.getCreatedBy().equals(userName) && !userRole.equals("user")) {
            logger.debug("User {} is not allowed to delete this comment", userName);
            throw new PermissionDeniedException("You are not allowed to update this comment");
        }

        logger.debug("Deleting comment with id {}", id);
        commentRepository.delete(comment);
    }
}
