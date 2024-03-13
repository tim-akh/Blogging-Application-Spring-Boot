package com.timakh.blog_app.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timakh.blog_app.exception.ResourceNotFoundException;
import com.timakh.blog_app.model.Comment;
import com.timakh.blog_app.model.Publication;
import com.timakh.blog_app.model.Role;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.service.CommentService;
import com.timakh.blog_app.service.PublicationService;
import com.timakh.blog_app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentIntTest {

    private final String COMMENTS_BASE_URL = "/api/v1/comments";


    @MockBean
    private CommentService commentService;

    @MockBean
    private UserService userService;
    @MockBean
    private PublicationService publicationService;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;



    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetCommentById() throws Exception {
        final Long id = 1L;

        final User user = new User(1L, "email1@ya.ru", "usr1", null, Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                user, new ArrayList<>(), new ArrayList<>());

        final Comment comment = new Comment(1L, "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                publication, user, new ArrayList<>()
        );

        when(commentService.getCommentById(id)).thenReturn(comment);

        mvc.perform(get(COMMENTS_BASE_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(comment.getId()))
                .andExpect(jsonPath("content").value(comment.getContent()))
                .andExpect(jsonPath("createdAt").value(comment.getCreatedAt().toString()))
                .andExpect(jsonPath("publication.id").value(comment.getPublication().getId()))
                .andExpect(jsonPath("user.id").value(comment.getUser().getId()))
                .andExpect(jsonPath("votes.size()").value(comment.getVotes().size()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenGettingCommentByNonExistingId() throws Exception {
        final Long id = 13L;

        when(commentService.getCommentById(id)).thenThrow(ResourceNotFoundException.class);

        mvc.perform(get(COMMENTS_BASE_URL + "/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldSaveComment() throws Exception {

        final User user = new User(1L, "email1@ya.ru", "usr1", null, Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                user, new ArrayList<>(), new ArrayList<>());

        final Comment comment = new Comment(1L, "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                publication, user, new ArrayList<>());


        when(commentService.saveComment(any(Comment.class))).thenReturn(comment);
        when(publicationService.getPublicationById(publication.getId())).thenReturn(publication);
        when(userService.getUserById(publication.getUser().getId())).thenReturn(user);


        mvc.perform(post(COMMENTS_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(comment.getId()))
                .andExpect(jsonPath("content").value(comment.getContent()))
                .andExpect(jsonPath("createdAt").value(comment.getCreatedAt().toString()))
                .andExpect(jsonPath("publication.id").value(comment.getPublication().getId()))
                .andExpect(jsonPath("user.id").value(comment.getUser().getId()))
                .andExpect(jsonPath("votes.size()").value(comment.getVotes().size()));

    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldUpdateComment() throws Exception {

        final Long id = 1L;
        final User user = new User(1L, "email1@ya.ru", "usr1", null, Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                user, new ArrayList<>(), new ArrayList<>());

        final Comment comment = new Comment(1L, "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                publication, user, new ArrayList<>());

        when(commentService.getCommentById(id)).thenReturn(comment);
        when(commentService.saveComment(any(Comment.class)))
                .thenAnswer((invocation) -> invocation.getArgument(0));

        mvc.perform(put(COMMENTS_BASE_URL + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(comment.getId()))
                .andExpect(jsonPath("content").value(comment.getContent()))
                .andExpect(jsonPath("createdAt").value(comment.getCreatedAt().toString()))
                .andExpect(jsonPath("publication.id").value(comment.getPublication().getId()))
                .andExpect(jsonPath("user.id").value(comment.getUser().getId()))
                .andExpect(jsonPath("votes.size()").value(comment.getVotes().size()));

    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturn404WhenUpdatingNonExistingComment() throws Exception {

        final Long id = 1L;
        final Comment comment = new Comment(1L, "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                new Publication(
                        "header1", "content1",
                        LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                        new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER, false)
                ),
                new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER, false),
                new ArrayList<>()
        );

        when(commentService.getCommentById(id)).thenThrow(ResourceNotFoundException.class);

        mvc.perform(put(COMMENTS_BASE_URL + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldDeleteComment() throws Exception {

        final Long id = 1L;
        final User user = new User(1L, "email1@ya.ru", "usr1", null, Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                user, new ArrayList<>(), new ArrayList<>());

        final Comment comment = new Comment(1L, "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                publication, user, new ArrayList<>());

        when(commentService.getCommentById(id)).thenReturn(comment);
        doNothing().when(commentService).deleteComment(comment);

        mvc.perform(delete(COMMENTS_BASE_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(comment.getId()))
                .andExpect(jsonPath("content").value(comment.getContent()))
                .andExpect(jsonPath("createdAt").value(comment.getCreatedAt().toString()))
                .andExpect(jsonPath("publication.id").value(comment.getPublication().getId()))
                .andExpect(jsonPath("user.id").value(comment.getUser().getId()))
                .andExpect(jsonPath("votes.size()").value(comment.getVotes().size()));

    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturn404WhenDeletingNonExistingComment() throws Exception {
        final Long id = 1L;

        when(commentService.getCommentById(id)).thenThrow(ResourceNotFoundException.class);

        mvc.perform(delete(COMMENTS_BASE_URL + "/" + id))
                .andExpect(status().isNotFound());
    }
}
