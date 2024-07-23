package by.jawh.postservice.business.dto;

import by.jawh.postservice.common.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    private Long id;

    private String text;

    private String pictureUrl;

    private Long profileId;

    private LocalDateTime timePublication;

    private List<LikeDto> like;

    private List<DislikeDto> dislike;

    private List<CommentEntity> comment;
}
