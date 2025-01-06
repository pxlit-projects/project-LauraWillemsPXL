package be.pxl.services.services;

import be.pxl.services.client.PostClient;
import be.pxl.services.domain.Review;
import be.pxl.services.exception.PermissionDeniedException;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService implements IReviewService {
    private final PostClient postClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void approvePost(Long postId, String userRole) {
        if (!userRole.equals("editor-in-chief")) {
            throw new PermissionDeniedException("Only editor-in-chief can review posts");
        }

        Review postReviewRequest = Review.builder()
                .review("APPROVED")
                .rejectionComment(null)
                .build();

        postClient.reviewPost(postId, postReviewRequest);
        rabbitTemplate.convertAndSend("notificationQueue", "Post with id " + postId + " is approved and published");
    }

    @Override
    public void rejectPost(Long postId, String rejectionComment, String userRole) {
        if (!userRole.equals("editor-in-chief")) {
            throw new PermissionDeniedException("Only editor-in-chief can review posts");
        }

        Review postReviewRequest = Review.builder()
                .review("REJECTED")
                .rejectionComment(rejectionComment)
                .build();

        postClient.reviewPost(postId, postReviewRequest);
        rabbitTemplate.convertAndSend("notificationQueue", "Post with id " + postId + " is rejected and needs revision");
    }
}
