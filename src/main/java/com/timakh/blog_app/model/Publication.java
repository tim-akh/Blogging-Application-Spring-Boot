package com.timakh.blog_app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    @JsonIgnoreProperties({"publications", "comments"})
    private User user;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"publication", "comment"})
    private List<Vote> votes;

    public Publication(String header, String content, LocalDateTime createdAt, User user) {
        this.header = header;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
    }
}
