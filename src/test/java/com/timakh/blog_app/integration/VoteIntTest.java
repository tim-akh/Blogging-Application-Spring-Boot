package com.timakh.blog_app.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timakh.blog_app.exception.ResourceNotFoundException;
import com.timakh.blog_app.model.*;
import com.timakh.blog_app.service.CommentService;
import com.timakh.blog_app.service.PublicationService;
import com.timakh.blog_app.service.UserService;
import com.timakh.blog_app.service.VoteService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VoteIntTest {

    private final String VOTES_BASE_URL = "/api/v1/votes";


    @MockBean
    private VoteService voteService;

    @MockBean
    private UserService userService;
    @MockBean
    private PublicationService publicationService;
    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;



    @Test
    @WithMockUser(roles = "USER")
    void shouldSaveVote() throws Exception {

        final User user = new User(1L, "email1@ya.ru", "usr1", null, Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                user, new ArrayList<>(), new ArrayList<>());

        final Comment comment = new Comment(1L, "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                publication, user, new ArrayList<>()
        );

        final Vote vote = new Vote(1L, user, 1, publication, comment);


        when(voteService.saveVote(any(Vote.class))).thenReturn(vote);
        when(commentService.getCommentById(comment.getId())).thenReturn(comment);
        when(publicationService.getPublicationById(publication.getId())).thenReturn(publication);
        when(userService.getUserById(publication.getUser().getId())).thenReturn(user);


        mvc.perform(post(VOTES_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(vote)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").value(vote.getId()))
                    .andExpect(jsonPath("user.id").value(vote.getUser().getId()))
                    .andExpect(jsonPath("dynamic").value(vote.getDynamic()))
                    .andExpect(jsonPath("publication.id").value(vote.getPublication().getId()))
                    .andExpect(jsonPath("comment.id").value(vote.getComment().getId()));

    }


    @Test
    @WithMockUser(roles = "USER")
    void shouldUpdateVote() throws Exception {

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

        final Vote vote = new Vote(1L, user, 1, publication, comment);

        when(voteService.getVoteById(id)).thenReturn(vote);
        when(voteService.saveVote(any(Vote.class))).thenAnswer((invocation) -> invocation.getArgument(0));

        mvc.perform(put(VOTES_BASE_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(vote)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").value(vote.getId()))
                    .andExpect(jsonPath("user.id").value(vote.getUser().getId()))
                    .andExpect(jsonPath("dynamic").value(vote.getDynamic()))
                    .andExpect(jsonPath("publication.id").value(vote.getPublication().getId()))
                    .andExpect(jsonPath("comment.id").value(vote.getComment().getId()));

    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturn404WhenUpdatingNonExistingVote() throws Exception {

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

        final Vote vote = new Vote(1L, user, 1, publication, comment);

        when(voteService.getVoteById(id)).thenThrow(ResourceNotFoundException.class);

        mvc.perform(put(VOTES_BASE_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(vote)))
                    .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldDeleteVote() throws Exception {

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

        final Vote vote = new Vote(1L, user, 1, publication, comment);

        when(voteService.getVoteById(id)).thenReturn(vote);
        doNothing().when(voteService).deleteVote(vote);

        mvc.perform(delete(VOTES_BASE_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(vote.getId()))
                .andExpect(jsonPath("user.id").value(vote.getUser().getId()))
                .andExpect(jsonPath("dynamic").value(vote.getDynamic()))
                .andExpect(jsonPath("publication.id").value(vote.getPublication().getId()))
                .andExpect(jsonPath("comment.id").value(vote.getComment().getId()));

    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturn404WhenDeletingNonExistingVote() throws Exception {
        final Long id = 1L;

        when(voteService.getVoteById(id)).thenThrow(ResourceNotFoundException.class);

        mvc.perform(delete(VOTES_BASE_URL + "/" + id))
                .andExpect(status().isNotFound());
    }

}
