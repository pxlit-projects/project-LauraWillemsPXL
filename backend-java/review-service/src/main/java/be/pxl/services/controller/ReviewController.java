package be.pxl.services.controller;

import be.pxl.services.domain.dto.RejectPostRequest;
import be.pxl.services.services.IReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/review")
@AllArgsConstructor
public class ReviewController {
    private final IReviewService reviewService;

    @PostMapping("/approve/{postId}")
    public ResponseEntity<Void> approvePost(@PathVariable Long postId,
                                            @RequestHeader(value = "User-Role") String userRole) {
        reviewService.approvePost(postId, userRole);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reject/{postId}")
    public ResponseEntity<Void> rejectPost(@PathVariable Long postId,
                                           @RequestBody RejectPostRequest rejectPostRequest,
                                           @RequestHeader(value = "User-Role") String userRole) {
        reviewService.rejectPost(postId, rejectPostRequest.getRejectionComment(), userRole);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
