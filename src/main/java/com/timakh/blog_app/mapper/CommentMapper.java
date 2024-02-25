package com.timakh.blog_app.mapper;

import com.timakh.blog_app.dto.CommentDto;
import com.timakh.blog_app.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper( CommentMapper.class );

    CommentDto commentToCommentDto(Comment comment);

    Comment commentDtoToComment(CommentDto commentDto);
}
