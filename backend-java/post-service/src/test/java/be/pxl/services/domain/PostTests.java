package be.pxl.services.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostTests {
    private Long id;
    private String title;
    private String content;
    private List<String> tags;
    private String author;
    private Date publishedDate;
    private boolean isDraft;
    private PostStatus status;
    private List<String> reviewNotifications;

    @BeforeEach
    public void setUp() {
        id = 1L;
        title = "title";
        content = "content";
        tags = List.of("tag1", "tag2");
        author = "author";
        publishedDate = new Date(2025, 1, 1);
        isDraft = false;
        status = PostStatus.PENDING;
        reviewNotifications = List.of("reviewNotification1", "reviewNotification2");
    }

    @Test
    public void testContructor_WithValidArguments_ShouldCreatePost() {
        Post post = new Post(id, title, content, tags, author, publishedDate, isDraft, status, reviewNotifications);

        assertEquals(id, post.getId());
        assertEquals(title, post.getTitle());
        assertEquals(content, post.getContent());
        assertEquals(tags, post.getTags());
        assertEquals(author, post.getAuthor());
        assertEquals(publishedDate, post.getPublishedDate());
        assertEquals(isDraft, post.isDraft());
        assertEquals(status, post.getStatus());
        assertEquals(reviewNotifications, post.getReviewNotifications());
    }

    @Test
    public void testBuilder_WithValidArguments_ShouldCreatePost() {
        Post post = Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .tags(tags)
                .author(author)
                .publishedDate(publishedDate)
                .isDraft(isDraft)
                .status(status)
                .reviewNotifications(reviewNotifications)
                .build();

        assertEquals(id, post.getId());
        assertEquals(title, post.getTitle());
        assertEquals(content, post.getContent());
        assertEquals(tags, post.getTags());
        assertEquals(author, post.getAuthor());
        assertEquals(publishedDate, post.getPublishedDate());
        assertEquals(isDraft, post.isDraft());
        assertEquals(status, post.getStatus());
        assertEquals(reviewNotifications, post.getReviewNotifications());
    }
}
