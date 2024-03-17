package com.timakh.blog_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.timakh.blog_app.model.User;
import lombok.Data;

@Data
public class VoteDto {

    private Long id;
    @JsonIgnoreProperties({"publications", "comments"})
    private User user;
    private Integer dynamic;
    @JsonIgnoreProperties({"user", "comments", "votes"})
    private PublicationDto publication;
    @JsonIgnoreProperties({"user", "publication", "votes"})
    private CommentDto comment;
}
