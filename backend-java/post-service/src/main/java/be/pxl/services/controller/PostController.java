package be.pxl.services.controller;

import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.services.interfaces.IPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}
