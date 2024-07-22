package by.jawh.newsfeedservice.common.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.lang.annotation.Documented;
import java.time.LocalDateTime;

@Document(indexName = "posts")
@Getter
@Setter
public class PostEs {

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
