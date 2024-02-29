package com.timakh.blog_app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "comment", schema = "public")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content", length = 255)
    @NotNull
    private String content;
    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "publication_id", referencedColumnName = "id")
    @NotNull
    private Publication publication;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    @JsonIgnoreProperties({"publications", "comments"})
    private User user;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"publication", "comment"})
    private List<Vote> votes;

    public Comment(String content, LocalDateTime createdAt, Publication publication, User user) {
        this.content = content;
        this.createdAt = createdAt;
        this.publication = publication;
        this.user = user;
    }
}
