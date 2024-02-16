package com.timakh.blog_app.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "vote", schema = "public", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "publication_id", "comment_id" }) })
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private User user;
    @Column(name = "vote")
    @NotNull
    private int vote;
    @ManyToOne
    @JoinColumn(name = "publication_id", referencedColumnName = "id")
    private Publication publication;
    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Comment comment;



}
