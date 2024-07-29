package by.jawh.postservice.business.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

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

    private Set<LikeDto> like;


    private Set<CommentResponseDto> comment;
}
