package by.jawh.postservice.business.mapper;

import by.jawh.postservice.business.dto.LikeDto;
import by.jawh.postservice.common.entity.CommentLikeEntity;
import by.jawh.postservice.common.entity.PostLikeEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface LikeMapper {

    // * post
    LikeDto postLikeEntityToDto(PostLikeEntity likeEntity);

    PostLikeEntity likeDtoToEntity(LikeDto likeDto);

    // * comment
    LikeDto commentLikeEntityToDto(CommentLikeEntity likeEntity);

    CommentLikeEntity commentLikeDtoToEntity(LikeDto likeDto);
}
