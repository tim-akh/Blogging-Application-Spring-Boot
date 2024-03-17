package com.timakh.blog_app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "vote", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "publication_id", "comment_id" })
})
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    @JsonIgnoreProperties({"publications", "comments"})
    private User user;

    @Column(name = "dynamic")
    @NotNull
    private Integer dynamic;

    @ManyToOne
    @JoinColumn(name = "publication_id", referencedColumnName = "id")
    private Publication publication;

    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Comment comment;

    public Vote(User user, Integer dynamic, Publication publication, Comment comment) {
        this.user = user;
        this.dynamic = dynamic;
        this.publication = publication;
        this.comment = comment;
    }
}