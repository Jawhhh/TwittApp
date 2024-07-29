package by.jawh.postservice.business.mapper;

import by.jawh.postservice.business.dto.CommentRequestDto;
import by.jawh.postservice.business.dto.CommentResponseDto;
import by.jawh.postservice.common.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        uses = {LikeMapper.class, PostMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {

    CommentEntity responseDtoToEntity(CommentResponseDto commentResponseDto);

    CommentEntity requestDtoToEntity(CommentRequestDto commentRequestDto);

    CommentResponseDto entityToResponseDto(CommentEntity commentEntity);

    void updateEntityFromDto(CommentRequestDto dto, @MappingTarget CommentEntity entity);
}
