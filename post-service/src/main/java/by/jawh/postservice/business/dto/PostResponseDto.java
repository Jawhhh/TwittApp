package by.jawh.postservice.business.dto;

import by.jawh.postservice.common.entity.CommentEntity;
import by.jawh.postservice.common.entity.DislikeEntity;
import by.jawh.postservice.common.entity.LikeEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
