package by.jawh.postservice.business.service.impl;

import by.jawh.postservice.business.service.LikeService;
import by.jawh.postservice.common.entity.CommentEntity;
import by.jawh.postservice.common.entity.LikeEntity;
import by.jawh.postservice.common.entity.PostEntity;
import by.jawh.postservice.common.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Transactional
    @Override
    public void addOrRemoveLikeOnPost(Map<Long, LikeEntity> likeList, Long profileId, PostEntity postEntity) {

        if (likeList.get(profileId) == null) {
            LikeEntity like = LikeEntity.builder().profileId(profileId).build();
            likeRepository.saveAndFlush(like);

            likeList.put(like.getProfileId(), like);
            postEntity.setLike(likeList);
        } else {
            likeList.remove(profileId);
            postEntity.setLike(likeList);
        }
    }

    @Transactional
    @Override
    public void addOrRemoveLikeOnComment(Map<Long, LikeEntity> likeList, Long profileId, CommentEntity commentEntity) {

        if (likeList.get(profileId) == null) {
            LikeEntity like = LikeEntity.builder().profileId(profileId).build();
            likeRepository.saveAndFlush(like);

            likeList.put(like.getProfileId(), like);
            commentEntity.setLike(likeList);
        } else {
            likeList.remove(profileId);
            commentEntity.setLike(likeList);
        }
    }
}
