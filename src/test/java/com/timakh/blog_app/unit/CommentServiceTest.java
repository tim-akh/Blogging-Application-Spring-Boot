package com.timakh.blog_app.unit;

import com.timakh.blog_app.model.Comment;
import com.timakh.blog_app.model.Publication;
import com.timakh.blog_app.model.Role;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.repository.CommentRepository;
import com.timakh.blog_app.repository.PublicationRepository;
import com.timakh.blog_app.service.CommentService;
import com.timakh.blog_app.service.PublicationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    private CommentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CommentService(commentRepository);
    }


    @Test
    void shouldGetCommentById() {
        final Long id = 1L;
        final Comment comment = new Comment(1L, "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                new Publication(
                        "header1", "content1",
                        LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                        new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER, false)
                ),
                new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER, false),
                new ArrayList<>()
        );

        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));

        final Optional<Comment> expectedOptional = Optional.ofNullable(underTest.getCommentById(id));

        Assertions.assertNotNull(expectedOptional);
    }


    @Test
    void shouldSaveCommentSuccessfully() {
        final Comment comment = new Comment(1L, "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                new Publication(
                        "header1", "content1",
                        LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                        new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER, false)
                ),
                new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER, false),
                new ArrayList<>()
        );

        when(commentRepository.save(comment)).thenReturn(comment);

        final Comment savedComment = underTest.saveComment(comment);

        Assertions.assertNotNull(savedComment);
        verify(commentRepository).save(any(Comment.class));
    }


    @Test
    void shouldDeleteComment() {
        final Comment comment = new Comment(1L, "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                new Publication(
                        "header1", "content1",
                        LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                        new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER, false)
                ),
                new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER, false),
                new ArrayList<>()
        );

        underTest.deleteComment(comment);

        verify(commentRepository).delete(comment);
    }

}
