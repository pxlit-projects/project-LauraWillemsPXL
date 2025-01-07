package be.pxl.services.services;

import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;

import java.util.List;

public interface ICommentService {
    CommentResponse addComment(CommentRequest commentRequest);
    List<CommentResponse> getAllComments();
    CommentResponse updateComment(Long id, CommentRequest commentRequest, String userRole, String userName);
    void deleteComment(Long id, String userRole, String userName);
}
