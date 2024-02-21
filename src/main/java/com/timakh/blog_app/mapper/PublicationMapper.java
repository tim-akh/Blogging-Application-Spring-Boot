package com.timakh.blog_app.mapper;

import com.timakh.blog_app.dto.PublicationDto;
import com.timakh.blog_app.model.Publication;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PublicationMapper {

    PublicationMapper INSTANCE = Mappers.getMapper( PublicationMapper.class );

    PublicationDto publicationToPublicationDto(Publication publication);
    Publication publicationDtoToPublication(PublicationDto publicationDto);

    List<PublicationDto> publicationListToPublicationDtoList(List<Publication> publications);
}
