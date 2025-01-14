package be.pxl.services.controller;

import be.pxl.services.domain.Comment;
import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.repository.ICommentRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class CommentControllerTests {
    private Comment comment;
    private Comment comment2;
    private CommentRequest commentRequest;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ICommentRepository commentRepository;

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
        comment = Comment.builder()
                .postId(1L)
                .comment("This is a comment")
                .createdBy("John Doe")
                .build();

        commentRequest = CommentRequest.builder()
                .postId(1L)
                .comment("This is a comment")
                .createdBy("John Doe")
                .build();

        comment2 = Comment.builder()
                .postId(1L)
                .comment("This is a comment")
                .createdBy("Jane Doe")
                .build();
    }

    @AfterEach
    public void tearDown() {
        commentRepository.deleteAll();
    }

    @Test
    public void testGetAllComments() throws Exception {
        commentRepository.save(comment);
        commentRepository.save(comment2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/comment"))
                .andExpect(status().isOk());

        assertEquals(2, commentRepository.findAll().size());
    }

    @Test
    public void testUpdateComment() throws Exception {
        commentRepository.save(comment);
        commentRequest.setComment("Updated comment");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/comment/update/{id}", comment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentRequest))
                        .header("User-Role", "user")
                        .header("User-Name", "John Doe"))
                .andExpect(status().isOk());

        assertEquals("Updated comment", commentRepository.findById(comment.getId()).get().getComment());
    }

    @Test
    public void testDeleteComment() throws Exception {
        commentRepository.save(comment);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/comment/delete/{id}", comment.getId())
                        .header("User-Role", "user")
                        .header("User-Name", "John Doe"))
                .andExpect(status().isOk());

        assertEquals(0, commentRepository.findAll().size());
    }
}
