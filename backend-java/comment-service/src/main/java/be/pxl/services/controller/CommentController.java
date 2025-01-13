package be.pxl.services.controller;

import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.services.ICommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final ICommentService commentService;
    private final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest commentRequest) {
        logger.debug("Adding a new comment with details: {}", commentRequest);
        return new ResponseEntity<>(commentService.addComment(commentRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllComments() {
        logger.debug("Retrieving all comments");
        return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id,
                                                         @RequestBody CommentRequest commentRequest,
                                                         @RequestHeader(value = "User-Role") String userRole,
                                                         @RequestHeader(value = "User-Name") String userName) {
        logger.debug("Updating comment with id {} to: {}", id, commentRequest);
        return new ResponseEntity<>(commentService.updateComment(id, commentRequest, userRole, userName), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long id,
                                                    @RequestHeader(value = "User-Role") String userRole,
                                                    @RequestHeader(value = "User-Name") String userName) {
        logger.debug("Deleting comment with id {}", id);
        commentService.deleteComment(id, userRole, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
