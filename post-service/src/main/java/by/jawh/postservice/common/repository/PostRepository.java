package by.jawh.postservice.common.repository;

import by.jawh.postservice.common.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findAllByProfileId(Long profileId);
}
