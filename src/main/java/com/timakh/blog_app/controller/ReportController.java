package com.timakh.blog_app.controller;

import com.timakh.blog_app.dto.ReportDto;
import com.timakh.blog_app.exception.EmptyAddressException;
import com.timakh.blog_app.mapper.ReportMapper;
import com.timakh.blog_app.model.*;
import com.timakh.blog_app.service.CommentService;
import com.timakh.blog_app.service.PublicationService;
import com.timakh.blog_app.service.ReportService;
import com.timakh.blog_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;
    private final PublicationService publicationService;
    private final CommentService commentService;

    private final ReportMapper reportMapper;

    @GetMapping
    public ResponseEntity<List<ReportDto>> getAllReports() {
        return new ResponseEntity<>(
                reportMapper.reportListToReportDtoList(reportService.getAllReports()),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ReportDto> createReport(@RequestBody ReportDto reportDto) {

        User user = userService.getUserById(reportDto.getUser().getId());
        Publication publication = null;
        Comment comment = null;

        if (reportDto.getPublication() != null) {
            publication = publicationService.getPublicationById(reportDto.getPublication().getId());
        } else if (reportDto.getComment() != null) {
            comment = commentService.getCommentById(reportDto.getComment().getId());
        } else throw new EmptyAddressException("Vote does not address any content");

        return new ResponseEntity<>(
                reportMapper.reportToReportDto(reportService.saveReport(new Report(
                        reportDto.getReason(),
                        user,
                        publication,
                        comment
                        )
                )), HttpStatus.OK
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ReportDto> deleteReport(@PathVariable Long id) {
        Report report = reportService.getReportById(id);
        reportService.deleteReport(report);
        return new ResponseEntity<>(reportMapper.reportToReportDto(report), HttpStatus.OK);
    }


}
