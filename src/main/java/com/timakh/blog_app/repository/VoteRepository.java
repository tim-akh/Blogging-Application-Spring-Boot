package com.timakh.blog_app.repository;

import com.timakh.blog_app.model.Comment;
import com.timakh.blog_app.model.Publication;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findById(Long id);

    Optional<Vote> findByUserAndPublicationAndComment(User user, Publication publication, Comment comment);
}
