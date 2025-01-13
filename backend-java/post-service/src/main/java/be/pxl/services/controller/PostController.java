package be.pxl.services.controller;

import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.services.IPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final IPostService postService;
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @PostMapping
    public ResponseEntity<PostResponse> addPost(@RequestBody @Valid PostRequest postRequest) {
        logger.debug("Adding a new post with details: {}", postRequest);
        return new ResponseEntity<>(postService.addPost(postRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(@RequestHeader(value = "User-Role") String userRole,
                                            @RequestHeader(value = "User-Name") String userName) {
        logger.debug("Getting all posts of {}", userName);
        return new ResponseEntity<>(postService.getAllPosts(userRole, userName), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        logger.debug("Finding post with id {}", id);
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @GetMapping("/drafts")
    public ResponseEntity<List<PostResponse>> getAllDrafts(@RequestHeader(value = "User-Role") String userRole,
                                            @RequestHeader(value = "User-Name") String userName) {
        logger.debug("Getting all drafts of {}", userName);
        return new ResponseEntity<>(postService.getAllDrafts(userRole, userName), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PostResponse> updateDraft(@PathVariable Long id,
                                                    @RequestBody @Valid PostRequest postRequest,
                                                    @RequestHeader(value = "User-Role") String userRole,
                                                    @RequestHeader(value = "User-Name") String userName) {
        logger.debug("Updating draft with id {} to: {}", id, postRequest);
        return new ResponseEntity<>(postService.updatePost(id, postRequest, userRole, userName), HttpStatus.OK);
    }

    @DeleteMapping("/draft/{id}")
    public ResponseEntity<HttpStatus> deleteDraft(@PathVariable Long id,
                                           @RequestHeader(value = "User-Role") String userRole,
                                           @RequestHeader(value = "User-Name") String userName) {
        logger.debug("Deleting draft with id {}", id);
        postService.deleteDraft(id, userRole, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/review")
    @ResponseStatus(HttpStatus.OK)
    public void reviewPost(@PathVariable Long id, @RequestBody ReviewRequest reviewRequest) {
        logger.debug("Reviewing post with id {}: {}", id, reviewRequest);
        postService.reviewPost(id, reviewRequest);
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<String>> getNotificationsOfAuthor(@RequestHeader(value = "User-Role") String userRole,
                                                                 @RequestHeader(value = "User-Name") String userName) {
        logger.debug("Getting notifications of author {}", userName);
        return new ResponseEntity<>(postService.getNotificationsOfAuthor(userRole, userName), HttpStatus.OK);
    }
}
