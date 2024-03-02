package com.timakh.blog_app.controller;

import com.timakh.blog_app.dto.PublicationDto;
import com.timakh.blog_app.exception.ResourceNotFoundException;
import com.timakh.blog_app.mapper.PublicationMapper;
import com.timakh.blog_app.model.Publication;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.service.PublicationService;
import com.timakh.blog_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/v1/publications")
public class PublicationController {

    private final PublicationService publicationService;
    private final UserService userService;
    private final PublicationMapper publicationMapper;

    @GetMapping
    public ResponseEntity<List<PublicationDto>> getAllPublications() {
        return new ResponseEntity<>(
//                publicationService.getAllPublications()
//                        .stream()
//                        .map(publicationMapper::publicationToPublicationDto)
//                        .collect(Collectors.toList()),
                publicationMapper.publicationListToPublicationDtoList(publicationService.getAllPublications()),
                HttpStatus.OK
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PublicationDto> createPublication(@RequestBody PublicationDto publicationDto) {

        User user = userService.getUserById(publicationDto.getUser().getId());

        return new ResponseEntity<>(
                publicationMapper.publicationToPublicationDto(publicationService.savePublication(new Publication(
                publicationDto.getHeader(),
                publicationDto.getContent(),
                LocalDateTime.now(),
                user
            )
        )), HttpStatus.OK
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<PublicationDto> getPublicationById(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(
                publicationMapper.publicationToPublicationDto(publicationService.getPublicationById(id)),
                HttpStatus.OK
        );
    }


    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PublicationDto> updatePublication(@PathVariable Long id,
                                                            @RequestBody PublicationDto publicationDto) {
        Publication publication = publicationService.getPublicationById(id);

        publication.setHeader(publicationDto.getHeader());
        publication.setContent(publicationDto.getContent());

        return new ResponseEntity<>(
                publicationMapper.publicationToPublicationDto(publicationService.savePublication(publication)),
                HttpStatus.OK
        );

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PublicationDto> deletePublication(@PathVariable Long id) {
        Publication publication = publicationService.getPublicationById(id);
        publicationService.deletePublication(publication);
        return new ResponseEntity<>(
                publicationMapper.publicationToPublicationDto(publication), HttpStatus.OK
        );
    }

}
