package be.pxl.services.services.interfaces;

import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;

public interface IPostService {
    PostResponse addPost(PostRequest postRequest);
}
