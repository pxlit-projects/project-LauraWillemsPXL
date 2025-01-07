package be.pxl.services.controller;

import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.services.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final ICommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest commentRequest) {
        return new ResponseEntity<>(commentService.addComment(commentRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllComments() {
        return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id,
                                                         @RequestBody CommentRequest commentRequest,
                                                         @RequestHeader(value = "User-Role") String userRole,
                                                         @RequestHeader(value = "User-Name") String userName) {

        return new ResponseEntity<>(commentService.updateComment(id, commentRequest, userRole, userName), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long id,
                                                    @RequestHeader(value = "User-Role") String userRole,
                                                    @RequestHeader(value = "User-Name") String userName) {

        commentService.deleteComment(id, userRole, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
