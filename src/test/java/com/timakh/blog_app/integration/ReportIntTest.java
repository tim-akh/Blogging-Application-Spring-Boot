package com.timakh.blog_app.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timakh.blog_app.exception.ResourceNotFoundException;
import com.timakh.blog_app.model.*;
import com.timakh.blog_app.service.CommentService;
import com.timakh.blog_app.service.PublicationService;
import com.timakh.blog_app.service.ReportService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportIntTest {

    private final String REPORTS_BASE_URL = "/api/v1/reports";

    @MockBean
    private ReportService reportService;

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

    private List<Report> reports;

    @BeforeEach
    void setUp() {
        reports = new ArrayList<>();

        final User user = new User(1L, "email1@ya.ru", "usr1", null, Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                user, new ArrayList<>(), new ArrayList<>());

        final Comment comment = new Comment(1L, "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                publication, user, new ArrayList<>()
        );

        reports.add(new Report(1L, "reason1", user, publication, comment));
        reports.add(new Report(2L, "reason2", user, publication, comment));
        reports.add(new Report(3L, "reason3", user, publication, comment));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllReports() throws Exception {
        when(reportService.getAllReports()).thenReturn(reports);

        mvc.perform(get(REPORTS_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(reports.size()));
    }

    @Test
    @WithMockUser
    void shouldReturn403WhenGettingReportsWithoutAdmin() throws Exception {

        mvc.perform(get(REPORTS_BASE_URL))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldSaveReport() throws Exception {

        final User user = new User(1L, "email1@ya.ru", "usr1", null, Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                user, new ArrayList<>(), new ArrayList<>());

        final Comment comment = new Comment(1L, "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                publication, user, new ArrayList<>()
        );

        Report report = new Report(1L, "reason1", user, publication, comment);


        when(reportService.saveReport(any(Report.class))).thenReturn(report);
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(publicationService.getPublicationById(publication.getId())).thenReturn(publication);
        when(commentService.getCommentById(comment.getId())).thenReturn(comment);


        mvc.perform(post(REPORTS_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(report)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").value(report.getId()))
                    .andExpect(jsonPath("reason").value(report.getReason()))
                    .andExpect(jsonPath("user.id").value(report.getUser().getId()))
                    .andExpect(jsonPath("publication.id").value(report.getPublication().getId()))
                    .andExpect(jsonPath("comment.id").value(report.getComment().getId()));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn403WhenSavingReportWithAdmin() throws Exception {

        final User user = new User(1L, "email1@ya.ru", "usr1", null, Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                user, new ArrayList<>(), new ArrayList<>());

        final Comment comment = new Comment(1L, "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                publication, user, new ArrayList<>()
        );

        Report report = new Report(1L, "reason1", user, publication, comment);


        when(reportService.saveReport(any(Report.class))).thenReturn(report);
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(publicationService.getPublicationById(publication.getId())).thenReturn(publication);
        when(commentService.getCommentById(comment.getId())).thenReturn(comment);


        mvc.perform(post(REPORTS_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(report)))
                    .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteReport() throws Exception {

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

        Report report = new Report(1L, "reason1", user, publication, comment);

        when(reportService.getReportById(id)).thenReturn(report);
        doNothing().when(reportService).deleteReport(report);

        mvc.perform(delete(REPORTS_BASE_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(report.getId()))
                .andExpect(jsonPath("reason").value(report.getReason()))
                .andExpect(jsonPath("user.id").value(report.getUser().getId()))
                .andExpect(jsonPath("publication.id").value(report.getPublication().getId()))
                .andExpect(jsonPath("comment.id").value(report.getComment().getId()));

    }

    @Test
    @WithMockUser
    void shouldReturn404WhenDeletingReportWithoutAdmin() throws Exception {
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

        Report report = new Report(1L, "reason1", user, publication, comment);

        when(reportService.getReportById(id)).thenReturn(report);
        doNothing().when(reportService).deleteReport(report);

        mvc.perform(delete(REPORTS_BASE_URL + "/" + id))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn403WhenDeletingNonExistingComment() throws Exception {
        final Long id = 1L;

        when(reportService.getReportById(id)).thenThrow(ResourceNotFoundException.class);

        mvc.perform(delete(REPORTS_BASE_URL + "/" + id))
                .andExpect(status().isNotFound());
    }

}
