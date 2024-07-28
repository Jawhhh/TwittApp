package by.jawh.postservice.common.repository;

import by.jawh.postservice.common.entity.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findAllByProfileId(Long profileId);

    Page<PostEntity> findAllByProfileId(Long profileId, Pageable pageable);

    @Query("select p from PostEntity p")
    List<PostEntity> findAll();

    @Override
    Page<PostEntity> findAll(Pageable pageable);
}
