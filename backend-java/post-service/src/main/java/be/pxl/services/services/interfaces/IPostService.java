package be.pxl.services.services.interfaces;

import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;

import java.util.List;

public interface IPostService {
    PostResponse addPost(PostRequest postRequest);
    List<PostResponse> getAllPosts(String userRole, String userName);
}
