package be.pxl.services.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private List<String> tags;
    private String author;
    private Date publishedDate;
}
