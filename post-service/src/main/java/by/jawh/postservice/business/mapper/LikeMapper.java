package by.jawh.postservice.business.mapper;

import by.jawh.postservice.business.dto.LikeDto;
import by.jawh.postservice.common.entity.LikeEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface LikeMapper {


    LikeDto likeEntityToDto(LikeEntity likeEntity);

    LikeEntity likeDtoToEntity(LikeDto likeDto);
}
