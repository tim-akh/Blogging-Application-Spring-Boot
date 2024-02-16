package com.timakh.blog_app.controller;

import com.timakh.blog_app.model.Publication;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/publications")
public class PublicationController {

    private final PublicationService publicationService;

    @GetMapping
    public List<Publication> getAllPublications() {
        return publicationService.getAllPublications();
    }

    @PostMapping
    public Publication createPublication(@RequestBody Publication publication) {
        return publicationService.savePublication(publication);
    }
}
