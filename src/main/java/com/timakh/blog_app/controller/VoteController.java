package com.timakh.blog_app.controller;

import com.timakh.blog_app.dto.UserDto;
import com.timakh.blog_app.dto.VoteDto;
import com.timakh.blog_app.exception.EmptyAddressException;
import com.timakh.blog_app.mapper.VoteMapper;
import com.timakh.blog_app.model.Comment;
import com.timakh.blog_app.model.Publication;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.model.Vote;
import com.timakh.blog_app.service.CommentService;
import com.timakh.blog_app.service.PublicationService;
import com.timakh.blog_app.service.UserService;
import com.timakh.blog_app.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/v1/votes")
public class VoteController {

    private final VoteService voteService;
    private final UserService userService;
    private final PublicationService publicationService;
    private final CommentService commentService;

    private final VoteMapper voteMapper;


    @PostMapping
    public ResponseEntity<VoteDto> createVote(@RequestBody VoteDto voteDto) {

        User user = userService.getUserById(voteDto.getUser().getId());
        Publication publication = null;
        Comment comment = null;

        if (voteDto.getPublication() != null) {
            publication = publicationService.getPublicationById(voteDto.getPublication().getId());
        } else if (voteDto.getComment() != null) {
            comment = commentService.getCommentById(voteDto.getComment().getId());
        } else throw new EmptyAddressException("Vote does not address any content");

        return new ResponseEntity<>(
                voteMapper.voteToVoteDto(voteService.saveVote(new Vote(
                        user,
                        voteDto.getDynamic(),
                        publication,
                        comment
                        )
                )), HttpStatus.OK
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<VoteDto> updateVote(@PathVariable Long id, @RequestBody VoteDto voteDto) {
        Vote vote = voteService.getVoteById(id);

        vote.setDynamic(voteDto.getDynamic());

        return new ResponseEntity<>(voteMapper.voteToVoteDto(voteService.saveVote(vote)), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<VoteDto> deleteVote(@PathVariable Long id) {
        Vote vote = voteService.getVoteById(id);
        voteService.deleteVote(vote);
        return new ResponseEntity<>(voteMapper.voteToVoteDto(vote), HttpStatus.OK);
    }
}
