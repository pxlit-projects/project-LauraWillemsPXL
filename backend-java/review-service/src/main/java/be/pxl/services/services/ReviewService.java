package be.pxl.services.services;

import be.pxl.services.client.PostClient;
import be.pxl.services.domain.RejectionReview;
import be.pxl.services.domain.Review;
import be.pxl.services.domain.dto.RejectionReviewResponse;
import be.pxl.services.exception.PermissionDeniedException;
import be.pxl.services.repository.IReviewRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService implements IReviewService {
    private final PostClient postClient;
    private final IReviewRepository reviewRepository;
    private final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void approvePost(Long postId, String userRole) {
        if (!userRole.equals("editor-in-chief")) {
            logger.debug("Permission denied for user with role {}. Only the editor-in-chief can review a post", userRole);
            throw new PermissionDeniedException("Only editor-in-chief can review posts");
        }

        Review postReviewRequest = Review.builder()
                .review("APPROVED")
                .build();

        logger.debug("Post with id {} is approved and published", postId);

        postClient.reviewPost(postId, postReviewRequest);
        rabbitTemplate.convertAndSend("notificationQueue", "Post with id " + postId + " is approved and published");
    }

    @Override
    public void rejectPost(Long postId, String rejectionComment, String userRole) {
        if (!userRole.equals("editor-in-chief")) {
            logger.debug("Permission denied for user with role {}. Only the editor-in-chief can review a post", userRole);
            throw new PermissionDeniedException("Only editor-in-chief can review posts");
        }

        Review postReviewRequest = Review.builder()
                .review("REJECTED")
                .build();

        RejectionReview rejectionReview = RejectionReview.builder()
                .rejectionComment(rejectionComment)
                .postId(postId)
                .build();

        logger.debug("Post with id {} is rejected and needs revision", postId);

        reviewRepository.save(rejectionReview);
        postClient.reviewPost(postId, postReviewRequest);
        rabbitTemplate.convertAndSend("notificationQueue", "Post with id " + postId + " is rejected and needs revision");
    }

    @Override
    public RejectionReviewResponse getRejectionComment(Long postId, String userRole) {
        if (!userRole.equals("editor")) {
            logger.debug("Permission denied for user with role {}. Only the editor of the post may view the rejection comment", userRole);
            throw new PermissionDeniedException("Only editor-in-chief can review posts");
        }

        RejectionReview rejectionReview = reviewRepository.findByPostId(postId);
        return RejectionReviewResponse.builder()
                .rejectionComment(rejectionReview.getRejectionComment())
                .build();
    }
}
