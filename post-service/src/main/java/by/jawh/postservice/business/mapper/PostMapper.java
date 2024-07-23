package by.jawh.postservice.business.mapper;

import by.jawh.postservice.business.dto.PostRequestDto;
import by.jawh.postservice.business.dto.PostResponseDto;
import by.jawh.postservice.common.entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PostMapper {

    PostEntity requestDtoToEntity(PostRequestDto postRequestDto);

    PostEntity responseDtoToEntity(PostResponseDto postResponseDto);

    PostResponseDto entityToResponseDto(PostEntity postEntity);

    void updatePostFromDto(PostRequestDto dto, @MappingTarget PostEntity entity);
}
