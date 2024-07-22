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

    @OneToMany
    @Builder.Default
    Map<Long, LikeEntity> like = new HashMap<>();

    @OneToMany
    @Builder.Default
    Map<Long, DislikeEntity> dislike = new HashMap<>();

    @OneToMany
    @Builder.Default
    private List<CommentEntity> comment = new ArrayList<>();
}
