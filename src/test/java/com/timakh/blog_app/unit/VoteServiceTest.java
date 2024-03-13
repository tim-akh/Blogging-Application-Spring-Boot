package com.timakh.blog_app.unit;

import com.timakh.blog_app.model.*;
import com.timakh.blog_app.repository.VoteRepository;
import com.timakh.blog_app.service.VoteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    private VoteService underTest;

    @BeforeEach
    void setUp() {
        underTest = new VoteService(voteRepository);
    }

    @Test
    void shouldGetVoteById() {
        final Long id = 1L;
        final User user = new User(1L, "email1@ya.ru", "usr1", null, Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                user, new ArrayList<>(), new ArrayList<>());

        final Comment comment = new Comment(1L, "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                publication, user, new ArrayList<>()
        );

        final Vote vote = new Vote(1L, user, 1, publication, comment);

        when(voteRepository.findById(id)).thenReturn(Optional.of(vote));

        final Optional<Vote> expectedOptional = Optional.ofNullable(underTest.getVoteById(id));

        Assertions.assertNotNull(expectedOptional);
    }

    @Test
    void shouldSaveVoteSuccessfully() {
        final User user = new User(1L, "email1@ya.ru", "usr1", null, Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                user, new ArrayList<>(), new ArrayList<>());

        final Comment comment = new Comment(1L, "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                publication, user, new ArrayList<>()
        );

        final Vote vote = new Vote(1L, user, 1, publication, comment);

        when(voteRepository.save(vote)).thenReturn(vote);

        final Vote savedVote = underTest.saveVote(vote);

        Assertions.assertNotNull(savedVote);
        verify(voteRepository).save(any(Vote.class));
    }

    @Test
    void shouldDeleteComment() {
        final User user = new User(1L, "email1@ya.ru", "usr1", null, Role.ROLE_USER,
                false, new ArrayList<>(), new ArrayList<>());

        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                user, new ArrayList<>(), new ArrayList<>());

        final Comment comment = new Comment(1L, "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                publication, user, new ArrayList<>()
        );

        final Vote vote = new Vote(1L, user, 1, publication, comment);

        underTest.deleteVote(vote);

        verify(voteRepository).delete(vote);
    }

}
