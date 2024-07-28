package by.jawh.postservice.business.service.impl;

import by.jawh.postservice.business.service.LikeService;
import by.jawh.postservice.common.entity.CommentEntity;
import by.jawh.postservice.common.entity.LikeEntity;
import by.jawh.postservice.common.entity.PostEntity;
import by.jawh.postservice.common.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Transactional
    @Override
    public void addOrRemoveLikeOnPost(List<LikeEntity> likeList, Long profileId, PostEntity postEntity) {

        likeList.stream().forEach(like -> {
            if (Objects.equals(like.getProfileId(), profileId)) {
                likeList.remove(like);
                postEntity.setLike(likeList);
            } else {
                LikeEntity newLike = LikeEntity.builder().profileId(profileId).build();
                likeRepository.saveAndFlush(newLike);
                likeList.add(newLike);
                postEntity.setLike(likeList);
            }
        });
    }

    @Transactional
    @Override
    public void addOrRemoveLikeOnComment(List<LikeEntity> likeList, Long profileId, CommentEntity commentEntity) {

        likeList.stream().forEach(like -> {
            if (Objects.equals(like.getProfileId(), profileId)) {
                likeList.remove(like);
                commentEntity.setLike(likeList);
            } else {
                LikeEntity newLike = LikeEntity.builder().profileId(profileId).build();
                likeRepository.saveAndFlush(newLike);
                likeList.add(newLike);
                commentEntity.setLike(likeList);
            }
        });
    }
}
