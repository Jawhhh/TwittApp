package by.jawh.postservice.business.mapper;

import by.jawh.postservice.business.dto.DislikeDto;
import by.jawh.postservice.common.entity.DislikeEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DislikeMapper {


    DislikeDto dislikeEntityToDto(DislikeEntity dislikeEntity);

    DislikeEntity dislikeDtoToEntity(DislikeDto dislikeDto);
}
