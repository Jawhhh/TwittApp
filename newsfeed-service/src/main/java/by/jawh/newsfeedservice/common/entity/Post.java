package by.jawh.newsfeedservice.common.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@RedisHash("posts")
public class Post {

    @Id
    private Long id;
    private String text;
    private String pictureUrl;
    private Long profileId;
    private LocalDateTime timePublication;
    private Integer likes;
    private Integer dislikes;
    private Integer comments;
}
