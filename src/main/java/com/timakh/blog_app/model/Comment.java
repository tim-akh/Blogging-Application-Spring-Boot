package com.timakh.blog_app.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "comment", schema = "public")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content")
    @NotNull
    private String content;
    @Column(name = "created_at")
    @NotNull
    private Timestamp createdAt;
    @ManyToOne
    @JoinColumn(name = "publication_id", referencedColumnName = "id")
    private Publication publication;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Vote> votes;
}
