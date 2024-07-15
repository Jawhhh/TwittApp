package by.jawh.postservice.common.repository;

import by.jawh.postservice.common.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
}
