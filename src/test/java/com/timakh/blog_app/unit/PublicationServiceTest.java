package com.timakh.blog_app.unit;

import com.timakh.blog_app.model.Publication;
import com.timakh.blog_app.model.Role;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.repository.PublicationRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublicationServiceTest {

    @Mock
    private PublicationRepository publicationRepository;

    private PublicationService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PublicationService(publicationRepository);
    }


    @Test
    void shouldGetAllPublications() {
        List<Publication> publications = new ArrayList<>();

        publications.add(new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER, false),
                new ArrayList<>(), new ArrayList<>()));
        publications.add(new Publication(2L, "header2", "content2",
                LocalDateTime.of(2024, Month.FEBRUARY, 2, 2, 2, 2),
                new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER, false),
                new ArrayList<>(), new ArrayList<>()));
        publications.add(new Publication(3L, "header3", "content3",
                LocalDateTime.of(2024, Month.MARCH, 3, 3, 3, 3),
                new User("email2@ya.ru", "usr2", "pass", Role.ROLE_USER, false),
                new ArrayList<>(), new ArrayList<>()));


        when(publicationRepository.findAll()).thenReturn(publications);

        List<Publication> expected = underTest.getAllPublications();
        Assertions.assertEquals(publications, expected);
    }

    @Test
    void shouldGetPublicationById() {
        final Long id = 1L;
        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER, false),
                new ArrayList<>(), new ArrayList<>());

        when(publicationRepository.findById(id)).thenReturn(Optional.of(publication));

        final Optional<Publication> expectedOptional = Optional.ofNullable(underTest.getPublicationById(id));

        Assertions.assertNotNull(expectedOptional);
    }


    @Test
    void shouldSavePublicationSuccessfully() {
        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER, false),
                new ArrayList<>(), new ArrayList<>());

        when(publicationRepository.save(publication)).thenReturn(publication);

        final Publication savedPublication = underTest.savePublication(publication);

        Assertions.assertNotNull(savedPublication);
        verify(publicationRepository).save(any(Publication.class));
    }


    @Test
    void shouldDeletePublication() {
        final Publication publication = new Publication(1L, "header1", "content1",
                LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1),
                new User("email1@ya.ru", "usr1", "pass", Role.ROLE_USER, false),
                new ArrayList<>(), new ArrayList<>());

        underTest.deletePublication(publication);

        verify(publicationRepository).delete(publication);
    }

}
