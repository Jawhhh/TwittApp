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
    private StringBuilder text;

    private String pictureUrl;

    @Column(nullable = false)
    private LocalDateTime timePublication;

    @OneToMany
    @Builder.Default
    List<LikeEntity> like = new ArrayList<>();

    @OneToMany
    @Builder.Default
    List<DislikeEntity> dislike = new ArrayList<>();

}
