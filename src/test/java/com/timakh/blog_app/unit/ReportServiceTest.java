package com.timakh.blog_app.unit;

import com.timakh.blog_app.model.*;
import com.timakh.blog_app.repository.ReportRepository;
import com.timakh.blog_app.service.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    private ReportService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ReportService(reportRepository);
    }


    @Test
    void shouldGetAllReports() {
        List<Report> reports = new ArrayList<>();

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

        when(reportRepository.findAll()).thenReturn(reports);

        List<Report> expected = underTest.getAllReports();
        Assertions.assertEquals(reports, expected);
    }

    @Test
    void shouldGetReportById() {
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

        when(reportRepository.findById(id)).thenReturn(Optional.of(report));

        final Optional<Report> expectedOptional = Optional.ofNullable(underTest.getReportById(id));

        Assertions.assertNotNull(expectedOptional);
    }


    @Test
    void shouldSaveReportSuccessfully() {

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

        when(reportRepository.save(report)).thenReturn(report);

        final Report savedReport = underTest.saveReport(report);

        Assertions.assertNotNull(savedReport);
        verify(reportRepository).save(any(Report.class));
    }


    @Test
    void shouldDeleteReport() {
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

        underTest.deleteReport(report);

        verify(reportRepository).delete(report);
    }

}
