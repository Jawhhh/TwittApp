package by.jawh.newsfeedservice.common.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@Setter
@RedisHash("posts")
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
