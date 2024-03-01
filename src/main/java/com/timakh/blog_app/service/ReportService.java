package com.timakh.blog_app.service;

import com.timakh.blog_app.exception.ResourceNotFoundException;
import com.timakh.blog_app.model.Report;
import com.timakh.blog_app.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;


    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    public void deleteReport(Report report) {
        reportRepository.delete(report);
    }

    public Report getReportById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report with id=" + id + " was not found"));
    }

}
