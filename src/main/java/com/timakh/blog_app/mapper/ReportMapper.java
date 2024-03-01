package com.timakh.blog_app.mapper;

import com.timakh.blog_app.dto.ReportDto;
import com.timakh.blog_app.model.Report;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    ReportMapper INSTANCE = Mappers.getMapper( ReportMapper.class );

    ReportDto reportToReportDto(Report report);

    Report reportDtoToReport(ReportDto reportDto);

    List<ReportDto> reportListToReportDtoList(List<Report> reports);
}
