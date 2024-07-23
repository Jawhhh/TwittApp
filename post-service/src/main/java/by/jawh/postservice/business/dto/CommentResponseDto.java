package by.jawh.postservice.business.dto;

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
