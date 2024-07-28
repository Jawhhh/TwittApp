package by.jawh.postservice.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private String pictureUrl;

    private Long profileId;

    @Column(nullable = false)
    private LocalDateTime timePublication;

    @OneToMany(fetch = FetchType.LAZY)
    @Builder.Default
    @Column(name = "like_id")
    List<LikeEntity> like = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @Builder.Default
    @Column(name = "dislike_id")
    List<DislikeEntity> dislike = new ArrayList<>();

    @Builder.Default
    @Column(name = "comment_id")
    @OneToMany(fetch = FetchType.LAZY)
    private List<CommentEntity> comment = new ArrayList<>();
}
