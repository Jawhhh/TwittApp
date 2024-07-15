package by.jawh.postservice.common.repository;

import by.jawh.postservice.common.entity.DislikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DislikeRepository extends JpaRepository<DislikeEntity, Long> {
}
