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
    @Column(name = "like_id")
    List<LikeEntity> like = new ArrayList<>();

    @OneToMany
    @Builder.Default
    @Column(name = "dislike_id")
    List<DislikeEntity> dislike = new ArrayList<>();

}
