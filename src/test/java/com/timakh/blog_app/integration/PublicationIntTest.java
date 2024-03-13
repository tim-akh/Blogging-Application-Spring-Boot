package com.timakh.blog_app.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timakh.blog_app.exception.ResourceNotFoundException;
import com.timakh.blog_app.model.Publication;
import com.timakh.blog_app.model.Role;
import com.timakh.blog_app.model.User;
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
public class PublicationIntTest {

    private final String PUBLICATIONS_BASE_URL = "/api/v1/publications";


    @MockBean
    private PublicationService publicationService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    private List<Publication> publications;



    @BeforeEach
    void setUp() {
        publications = new ArrayList<>();
        publications.add(new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER, false),
                new ArrayList<>(), new ArrayList<>()));
        publications.add(new Publication(2L, "header2", "content2",
                LocalDateTime.of(2024, Month.FEBRUARY, 2, 2, 2, 2),
                new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER, false),
                new ArrayList<>(), new ArrayList<>()));
        publications.add(new Publication(3L, "header3", "content3",
                LocalDateTime.of(2024, Month.MARCH, 3, 3, 3, 3),
                new User("email2@ya.ru", "usr2", "pass", Role.ROLE_USER, false),
                new ArrayList<>(), new ArrayList<>()));
    }


    @Test
    @WithMockUser
    void shouldGetAllPublications() throws Exception {
        when(publicationService.getAllPublications()).thenReturn(publications);

        mvc.perform(get(PUBLICATIONS_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(publications.size()));
    }

    @Test
    @WithMockUser
    void shouldGetPublicationById() throws Exception {
        final Long id = 1L;
        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                new User("email1@ya.ru", "usr1", null, Role.ROLE_USER, false),
                new ArrayList<>(), new ArrayList<>());

        when(publicationService.getPublicationById(id)).thenReturn(publication);

        mvc.perform(get(PUBLICATIONS_BASE_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(publication.getId()))
                .andExpect(jsonPath("header").value(publication.getHeader()))
                .andExpect(jsonPath("content").value(publication.getContent()))
                .andExpect(jsonPath("createdAt").value(publication.getCreatedAt().toString()))
                .andExpect(jsonPath("user").value(publication.getUser()))
                .andExpect(jsonPath("comments.size()").value(publication.getComments().size()))
                .andExpect(jsonPath("votes.size()").value(publication.getVotes().size()));
    }

    @Test
    @WithMockUser
    void shouldReturn404WhenGettingPublicationByNonExistingId() throws Exception {
        final Long id = 13L;

        when(publicationService.getPublicationById(id)).thenThrow(ResourceNotFoundException.class);

        mvc.perform(get(PUBLICATIONS_BASE_URL + "/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldSavePublication() throws Exception {

        final User user = new User(1L, "email1@ya.ru", "usr1", null, Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
               user, new ArrayList<>(), new ArrayList<>());


        when(publicationService.savePublication(any(Publication.class))).thenReturn(publication);
        when(userService.getUserById(publication.getUser().getId())).thenReturn(user);


        mvc.perform(post(PUBLICATIONS_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(publication)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("header").value(publication.getHeader()))
                    .andExpect(jsonPath("content").value(publication.getContent()))
                    .andExpect(jsonPath("createdAt").value(publication.getCreatedAt().toString()))
                    .andExpect(jsonPath("user.id").value(publication.getUser().getId()))
                    .andExpect(jsonPath("comments.size()").value(publication.getComments().size()))
                    .andExpect(jsonPath("votes.size()").value(publication.getVotes().size()));

    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldUpdatePublication() throws Exception {

        final Long id = 1L;
        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                new User("email1@ya.ru", "usr1", null, Role.ROLE_USER, false),
                new ArrayList<>(), new ArrayList<>());

        when(publicationService.getPublicationById(id)).thenReturn(publication);
        when(publicationService.savePublication(any(Publication.class)))
                .thenAnswer((invocation) -> invocation.getArgument(0));

        mvc.perform(put(PUBLICATIONS_BASE_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(publication)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("header").value(publication.getHeader()))
                    .andExpect(jsonPath("content").value(publication.getContent()))
                    .andExpect(jsonPath("createdAt").value(publication.getCreatedAt().toString()))
                    .andExpect(jsonPath("user").value(publication.getUser()))
                    .andExpect(jsonPath("comments.size()").value(publication.getComments().size()))
                    .andExpect(jsonPath("votes.size()").value(publication.getVotes().size()));

    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturn404WhenUpdatingNonExistingPublication() throws Exception {

        final Long id = 1L;
        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER, false),
                new ArrayList<>(), new ArrayList<>());

        when(publicationService.getPublicationById(id)).thenThrow(ResourceNotFoundException.class);

        mvc.perform(put(PUBLICATIONS_BASE_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(publication)))
                    .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldDeletePublication() throws Exception {

        final Long id = 1L;
        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                new User("email1@ya.ru", "usr1", null, Role.ROLE_USER, false),
                new ArrayList<>(), new ArrayList<>());

        when(publicationService.getPublicationById(id)).thenReturn(publication);
        doNothing().when(publicationService).deletePublication(publication);

        mvc.perform(delete(PUBLICATIONS_BASE_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("header").value(publication.getHeader()))
                .andExpect(jsonPath("content").value(publication.getContent()))
                .andExpect(jsonPath("createdAt").value(publication.getCreatedAt().toString()))
                .andExpect(jsonPath("user").value(publication.getUser()))
                .andExpect(jsonPath("comments.size()").value(publication.getComments().size()))
                .andExpect(jsonPath("votes.size()").value(publication.getVotes().size()));

    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturn404WhenDeletingNonExistingPublication() throws Exception {
        final Long id = 1L;

        when(publicationService.getPublicationById(id)).thenThrow(ResourceNotFoundException.class);

        mvc.perform(delete(PUBLICATIONS_BASE_URL + "/" + id))
                .andExpect(status().isNotFound());
    }
}
