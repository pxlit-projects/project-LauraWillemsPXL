package be.pxl.services.services;

public interface IReviewService {
    void approvePost(Long postId, String userRole);
    void rejectPost(Long postId, String rejectionComment, String userRole);
}
