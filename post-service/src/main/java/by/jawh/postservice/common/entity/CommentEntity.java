package by.jawh.postservice.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long profileId;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private String text;

    private String pictureUrl;

    @Column(nullable = false)
    private LocalDateTime timePublication;

    @OneToMany
    @Builder.Default
    Map<Long, LikeEntity> like = new HashMap<>();

    @OneToMany
    @Builder.Default
    Map<Long, DislikeEntity> dislike = new HashMap<>();

}
