package by.jawh.postservice.business.dto;

import by.jawh.postservice.common.entity.CommentLikeEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;

    private Long profileId;

    private Long postId;

    private String text;

    private String pictureUrl;

    private LocalDateTime timePublication;

    List<CommentLikeEntity> like = new ArrayList<>();

}
