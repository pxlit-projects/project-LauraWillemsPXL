package be.pxl.services.client;

import be.pxl.services.domain.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "post-service")
public interface PostClient {
    @PutMapping("/api/post/{id}/review")
    void reviewPost(@PathVariable Long id, @RequestBody Review review);
}
