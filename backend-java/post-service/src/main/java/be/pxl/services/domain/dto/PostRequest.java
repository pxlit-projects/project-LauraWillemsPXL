package be.pxl.services.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotEmpty
    private List<String> tags;

    @NotBlank
    private String author;

    @NotNull
    @JsonProperty("draft")
    private boolean isDraft;
}
