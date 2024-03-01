package com.timakh.blog_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.timakh.blog_app.model.Comment;
import com.timakh.blog_app.model.Publication;
import com.timakh.blog_app.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Role role;
    private boolean banned;
    @JsonIgnoreProperties("user")
    private List<PublicationDto> publications;
    //@JsonIgnoreProperties("user")
    //private List<CommentDto> comments;
}
