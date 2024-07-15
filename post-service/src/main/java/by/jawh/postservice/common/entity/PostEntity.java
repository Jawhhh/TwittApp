package by.jawh.postservice.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private StringBuilder text;

    private String pictureUrl;

    private Long profileId;

    @Column(nullable = false)
    private LocalDateTime timePublication;

    @OneToMany
    @Builder.Default
    private List<LikeEntity> like = new ArrayList<>();

    @OneToMany
    @Builder.Default
    private List<DislikeEntity> dislike = new ArrayList<>();

    @OneToMany
    @Builder.Default
    private List<CommentEntity> comment = new ArrayList<>();
}
