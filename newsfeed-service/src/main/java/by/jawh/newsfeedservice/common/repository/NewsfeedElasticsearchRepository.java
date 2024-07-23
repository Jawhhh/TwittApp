package by.jawh.newsfeedservice.common.repository;

import by.jawh.newsfeedservice.common.entity.PostEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsfeedElasticsearchRepository extends ElasticsearchRepository<PostEs, Long> {

    Page<PostEs> findByProfileId(Long profileId, Pageable pageable);
}
