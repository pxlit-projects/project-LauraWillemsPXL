package be.pxl.services.services;

import be.pxl.services.domain.dto.RejectionReviewResponse;

public interface IReviewService {
    void approvePost(Long postId, String userRole);
    void rejectPost(Long postId, String rejectionComment, String userRole);
    RejectionReviewResponse getRejectionComment(Long postId, String userRole);
}
