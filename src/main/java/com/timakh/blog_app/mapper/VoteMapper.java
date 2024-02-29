package com.timakh.blog_app.mapper;

import com.timakh.blog_app.dto.VoteDto;
import com.timakh.blog_app.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    VoteMapper INSTANCE = Mappers.getMapper( VoteMapper.class );

    VoteDto voteToVoteDto(Vote vote);
    Vote voteDtoToVote(VoteDto voteDto);
}
