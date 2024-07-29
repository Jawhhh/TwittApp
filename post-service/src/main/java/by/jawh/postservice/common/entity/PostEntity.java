package by.jawh.postservice.common.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.EntityGraph;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@NamedEntityGraph(name = "withLikeAndComments", attributeNodes = {
        @NamedAttributeNode("like"),
        @NamedAttributeNode("comment")

})
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

    @JsonManagedReference
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<PostLikeEntity> like = new TreeSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<CommentEntity> comment = new TreeSet<>();
}
