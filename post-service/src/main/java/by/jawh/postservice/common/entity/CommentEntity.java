package by.jawh.postservice.common.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(name = "withLikeAndPost", attributeNodes = {
        @NamedAttributeNode("like"),
        @NamedAttributeNode("post")

})
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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Column(nullable = false)
    private String text;

    private String pictureUrl;

    @Column(nullable = false)
    private LocalDateTime timePublication;

    @JsonManagedReference
    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, orphanRemoval = true)
    List<CommentLikeEntity> like = new ArrayList<>();
}
