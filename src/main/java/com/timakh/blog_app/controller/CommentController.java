package com.timakh.blog_app.controller;

import com.timakh.blog_app.dto.CommentDto;
import com.timakh.blog_app.mapper.CommentMapper;
import com.timakh.blog_app.model.Comment;
import com.timakh.blog_app.model.Publication;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.service.CommentService;
import com.timakh.blog_app.service.PublicationService;
import com.timakh.blog_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final PublicationService publicationService;
    private final CommentMapper commentMapper;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {

        Publication publication = publicationService.getPublicationById(commentDto.getPublication().getId());
        User user = userService.getUserById(commentDto.getUser().getId());

        return new ResponseEntity<>(commentMapper.commentToCommentDto(commentService.saveComment(new Comment(
                commentDto.getContent(),
                LocalDateTime.now(),
                publication,
                user
            )
        )), HttpStatus.OK
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        return new ResponseEntity<>(
                commentMapper.commentToCommentDto(commentService.getCommentById(id)),
                HttpStatus.OK
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        Comment comment = commentService.getCommentById(id);

        comment.setContent(commentDto.getContent());

        return new ResponseEntity<>(
                commentMapper.commentToCommentDto(commentService.saveComment(comment)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        commentService.deleteComment(comment);
        return new ResponseEntity<>(commentMapper.commentToCommentDto(comment), HttpStatus.OK);
    }
}
