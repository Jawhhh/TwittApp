package by.jawh.postservice.business.dto;

import by.jawh.postservice.common.entity.DislikeEntity;
import by.jawh.postservice.common.entity.LikeEntity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    List<LikeEntity> like = new ArrayList<>();

    List<DislikeEntity> dislike = new ArrayList<>();
}
