package be.pxl.services.controller;

import be.pxl.services.domain.dto.RejectionReviewRequest;
import be.pxl.services.domain.dto.RejectionReviewResponse;
import be.pxl.services.services.IReviewService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/review")
@AllArgsConstructor
public class ReviewController {
    private final IReviewService reviewService;
    private final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @PostMapping("/approve/{postId}")
    public ResponseEntity<Void> approvePost(@PathVariable Long postId,
                                            @RequestHeader(value = "User-Role") String userRole) {
        reviewService.approvePost(postId, userRole);
        logger.debug("Post with id {} is approved", postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reject/{postId}")
    public ResponseEntity<Void> rejectPost(@PathVariable Long postId,
                                           @RequestBody RejectionReviewRequest rejectionReviewRequest,
                                           @RequestHeader(value = "User-Role") String userRole) {
        reviewService.rejectPost(postId, rejectionReviewRequest.getRejectionComment(), userRole);
        logger.debug("Post with id {} is rejected: {}", postId, rejectionReviewRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<RejectionReviewResponse> getRejectionComment(@PathVariable Long postId,
                                                                       @RequestHeader(value = "User-Role") String userRole) {
        logger.debug("Getting rejection comment for post with id {}", postId);
        return new ResponseEntity<>(reviewService.getRejectionComment(postId, userRole), HttpStatus.OK);
    }
}
