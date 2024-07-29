package by.jawh.postservice.common.repository;

import by.jawh.postservice.common.entity.CommentEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @EntityGraph("withLikeAndPost")
    List<CommentEntity> findAllByPostId(Long postId);

    @EntityGraph("withLikeAndPost")
    List<CommentEntity> findAllByProfileIdAndPostId(Long postId, Long profileId);

    @EntityGraph("withLikeAndPost")
    Optional<CommentEntity> findById(Long id);

    @EntityGraph("withLikeAndPost")
    Optional<CommentEntity> findByIdAndPostId(Long id, Long postId);
}
