package com.timakh.blog_app.controller;

import com.timakh.blog_app.exception.ResourceNotFoundException;
import com.timakh.blog_app.model.Publication;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.model.dto.PublicationRequest;
import com.timakh.blog_app.security.CustomUserDetails;
import com.timakh.blog_app.service.PublicationService;
import com.timakh.blog_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/publications")
public class PublicationController {

    private final PublicationService publicationService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Publication>> getAllPublications() {
        return new ResponseEntity<>(publicationService.getAllPublications(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Publication> createPublication(@RequestBody PublicationRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        User user = userService.getUserById(userDetails.getId());

        System.out.println(LocalDateTime.now());
        return new ResponseEntity<>(publicationService.savePublication(new Publication(
                request.getHeader(),
                request.getContent(),
                LocalDateTime.now(),
                user
            )
        ), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Publication> getPublicationById(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(publicationService.getPublicationById(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Publication> updatePublication(@PathVariable Long id, @RequestBody Publication publicationDetails) {
        Publication publication = publicationService.getPublicationById(id);

        publication.setHeader(publicationDetails.getHeader());
        publication.setContent(publicationDetails.getContent());

        return new ResponseEntity<>(publicationService.savePublication(publication), HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publication> deletePublication(@PathVariable Long id) {
        Publication publication = publicationService.getPublicationById(id);
        publicationService.deletePublication(publication);
        return new ResponseEntity<>(publication, HttpStatus.OK);
    }

}
