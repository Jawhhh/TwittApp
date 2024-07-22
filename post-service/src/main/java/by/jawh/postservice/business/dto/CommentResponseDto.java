package by.jawh.postservice.business.dto;

import by.jawh.postservice.common.entity.DislikeEntity;
import by.jawh.postservice.common.entity.LikeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentResponseDto {

    private Long id;

    private Long profileId;

    private Long postId;

    private String text;

    private String pictureUrl;

    private LocalDateTime timePublication;

    List<LikeDto> like;

    List<DislikeDto> dislike;
}
