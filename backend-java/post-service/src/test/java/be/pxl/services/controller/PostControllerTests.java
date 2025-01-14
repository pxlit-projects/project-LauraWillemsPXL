package be.pxl.services.controller;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.PostStatus;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.repository.IPostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class PostControllerTests {
    private Post post;
    private Post post2;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IPostRepository postRepository;

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:8.0.30");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    public void setUp() {
        post = Post.builder()
                .title("title")
                .content("content")
                .tags(List.of("tag1", "tag2"))
                .author("John Doe")
                .publishedDate(new Date(2025, 1, 1))
                .isDraft(false)
                .status(PostStatus.PENDING)
                .reviewNotifications(List.of("reviewNotification1", "reviewNotification2"))
                .build();

        post2 = Post.builder()
                .title("title 2")
                .content("content 2")
                .tags(List.of("tag1", "tag2"))
                .author("John Doe")
                .publishedDate(new Date(2025, 1, 1))
                .isDraft(false)
                .status(PostStatus.PENDING)
                .reviewNotifications(List.of("reviewNotification1", "reviewNotification2"))
                .build();
    }

    @AfterEach
    public void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    public void testAddPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated());

        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    public void testGetAllPosts() throws Exception {
        postRepository.save(post);
        postRepository.save(post2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/post")
                .header("User-Role", "user")
                .header("User-Name", "Alice Smith"))
                .andExpect(status().isOk());

        assertEquals(2, postRepository.findAll().size());
    }

    @Test
    public void testGetPostById() throws Exception {
        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/post/{id}", post.getId())
                .header("User-Role", "user")
                .header("User-Name", "Alice Smith"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllDrafts() throws Exception {
        post.setDraft(true);
        post2.setDraft(true);
        postRepository.save(post);
        postRepository.save(post2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/post/drafts")
                .header("User-Role", "editor")
                .header("User-Name", "John Doe"))
                .andExpect(status().isOk());

        assertEquals(2, postRepository.findAll().size());
    }

    @Test
    public void testUpdateDraft() throws Exception {
        postRepository.save(post);
        post.setTitle("Updated Title");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/post/update/{id}", post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post))
                .header("User-Role", "editor")
                .header("User-Name", "John Doe"))
                .andExpect(status().isOk());

        assertEquals("Updated Title", postRepository.findById(post.getId()).get().getTitle());
    }

    @Test
    public void testDeleteDraft() throws Exception {
        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/post/draft/{id}", post.getId())
                .header("User-Role", "editor")
                .header("User-Name", "John Doe"))
                .andExpect(status().isOk());

        assertEquals(0, postRepository.findAll().size());
    }

    @Test
    public void testReviewPost() throws Exception {
        postRepository.save(post);
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setReview("approved");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/post/{id}/review", post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewRequest))
                .header("User-Role", "editor-in-chief")
                .header("User-Name", "Alice Smith"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetNotificationsOfAuthor() throws Exception {
        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/post/notifications")
                .header("User-Role", "editor")
                .header("User-Name", "John Doe"))
                .andExpect(status().isOk());
    }
}
