package com.timakh.blog_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.model.Vote;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    @JsonIgnoreProperties({"comments"})
    private PublicationDto publication;
    @JsonIgnoreProperties({"publications", "comments"})
    private User user;
    @JsonIgnoreProperties({"publication", "comment"})
    private List<Vote> votes;

}
