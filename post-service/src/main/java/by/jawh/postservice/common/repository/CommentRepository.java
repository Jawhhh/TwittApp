package by.jawh.postservice.common.repository;

import by.jawh.postservice.business.dto.CommentResponseDto;
import by.jawh.postservice.common.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByPostId(Long postId);

    List<CommentEntity> findAllByProfileIdAndPostId(Long postId, Long profileId);

    Optional<CommentEntity> findById(Long id);

    Optional<CommentEntity> findByIdAndPostId(Long id, Long postId);
}
