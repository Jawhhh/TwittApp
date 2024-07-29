package by.jawh.postservice.common.repository;

import by.jawh.postservice.common.entity.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {

}
