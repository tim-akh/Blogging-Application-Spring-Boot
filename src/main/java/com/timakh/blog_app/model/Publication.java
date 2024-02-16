package com.timakh.blog_app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "publication", schema = "public")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "header")
    @NotNull
    private String header;
    @Column(name = "content", length = 65535)
    @NotNull
    private String content;
    @Column(name = "created_at")
    @NotNull
    private Timestamp createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private User user;
    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
    private List<Comment> comments;
    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
    private List<Vote> votes;
}
