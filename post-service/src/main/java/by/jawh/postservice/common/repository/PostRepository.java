package by.jawh.postservice.common.repository;

import by.jawh.postservice.common.entity.PostEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @EntityGraph("withLikeAndComments")
    List<PostEntity> findAllByProfileId(Long profileId);

    @EntityGraph("withLikeAndComments")
    Page<PostEntity> findAllByProfileId(Long profileId, Pageable pageable);

    @EntityGraph("withLikeAndComments")
    List<PostEntity> findAll();
}
