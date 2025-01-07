package be.pxl.services.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private Long postId;
    private String comment;
    private String createdBy;
    private Date createdAt;
}
