package com.timakh.blog_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.timakh.blog_app.model.Comment;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.model.Vote;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PublicationDto {

    private Long id;
    private String header;
    private String content;
    private LocalDateTime createdAt;
    @JsonIgnoreProperties("publications")
    private User user;
    private List<Comment> comments;
    private List<Vote> votes;
}
