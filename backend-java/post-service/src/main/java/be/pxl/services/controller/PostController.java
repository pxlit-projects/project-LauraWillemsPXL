package be.pxl.services.controller;

import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.services.interfaces.IPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final IPostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> addPost(@RequestBody @Valid PostRequest postRequest) {
        return new ResponseEntity<>(postService.addPost(postRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(@RequestHeader(value = "User-Role") String userRole,
                                            @RequestHeader(value = "User-Name") String userName) {
        return new ResponseEntity<>(postService.getAllPosts(userRole, userName), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @GetMapping("/drafts")
    public ResponseEntity<List<PostResponse>> getAllDrafts(@RequestHeader(value = "User-Role") String userRole,
                                            @RequestHeader(value = "User-Name") String userName) {
        return new ResponseEntity<>(postService.getAllDrafts(userRole, userName), HttpStatus.OK);
    }

    @PutMapping("/draft/{id}")
    public ResponseEntity<PostResponse> updateDraft(@PathVariable Long id,
                                                    @RequestBody @Valid PostRequest postRequest,
                                                    @RequestHeader(value = "User-Role") String userRole,
                                                    @RequestHeader(value = "User-Name") String userName) {

        return new ResponseEntity<>(postService.updateDraft(id, postRequest, userRole, userName), HttpStatus.OK);
    }

    @DeleteMapping("/draft/{id}")
    public ResponseEntity<HttpStatus> deleteDraft(@PathVariable Long id,
                                           @RequestHeader(value = "User-Role") String userRole,
                                           @RequestHeader(value = "User-Name") String userName) {

        postService.deleteDraft(id, userRole, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
