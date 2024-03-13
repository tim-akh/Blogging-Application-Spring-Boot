package com.timakh.blog_app.service;

import com.timakh.blog_app.exception.ResourceNotFoundException;
import com.timakh.blog_app.model.Comment;
import com.timakh.blog_app.model.Publication;
import com.timakh.blog_app.model.User;
import com.timakh.blog_app.model.Vote;
import com.timakh.blog_app.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VoteService {

    private final VoteRepository voteRepository;

    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public void deleteVote(Vote vote) {
        voteRepository.delete(vote);
    }

    public Vote getVoteById(Long id) {
        return voteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vote with id=" + id + " was not found"));
    }

}
