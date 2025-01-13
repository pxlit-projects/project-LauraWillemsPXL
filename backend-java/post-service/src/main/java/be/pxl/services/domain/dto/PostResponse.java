package be.pxl.services.domain.dto;

import be.pxl.services.domain.PostStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private List<String> tags;
    private String author;
    private Date publishedDate;
    @JsonProperty("draft")
    private boolean isDraft;
    private PostStatus status;
    private List<String> reviewNotifications;
}
