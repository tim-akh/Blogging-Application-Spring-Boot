package com.timakh.blog_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.timakh.blog_app.model.User;
import lombok.Data;

@Data
public class ReportDto {
    private Long id;
    private String reason;
    @JsonIgnoreProperties({"publications", "comments"})
    private User user;
    @JsonIgnoreProperties({"user", "comments", "votes"})
    private PublicationDto publication;
    @JsonIgnoreProperties({"user", "publication", "votes"})
    private CommentDto comment;
}
