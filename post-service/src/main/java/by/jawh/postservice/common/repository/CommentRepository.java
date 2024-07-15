package by.jawh.postservice.common.repository;

import by.jawh.postservice.common.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
