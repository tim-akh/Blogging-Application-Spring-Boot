package com.timakh.blog_app.service;

import com.timakh.blog_app.model.Publication;
import com.timakh.blog_app.repository.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PublicationService {

    private final PublicationRepository publicationRepository;

    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    public Publication savePublication(Publication publication) {
        return publicationRepository.save(publication);
    }

    public Publication getPublicationById(Long id) {
        return publicationRepository.findPublicationById(id);
    }


}
