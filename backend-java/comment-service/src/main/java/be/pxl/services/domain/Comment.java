package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    private Long postId;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String comment;

    private String createdBy;

    private Date createdAt;
}
