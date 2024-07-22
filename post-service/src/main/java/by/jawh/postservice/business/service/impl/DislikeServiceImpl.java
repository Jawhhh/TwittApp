package by.jawh.postservice.business.service.impl;

import by.jawh.postservice.business.service.DislikeService;
import by.jawh.postservice.common.entity.CommentEntity;
import by.jawh.postservice.common.entity.DislikeEntity;
import by.jawh.postservice.common.entity.PostEntity;
import by.jawh.postservice.common.repository.DislikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DislikeServiceImpl implements DislikeService {

    private final DislikeRepository dislikeRepository;

    @Transactional
    @Override
    public void addOrRemoveDislikeOnPost(Map<Long, DislikeEntity> dislikeList, Long profileId, PostEntity postEntity) {
        if (dislikeList.get(profileId) == null) {
            DislikeEntity dislike = DislikeEntity.builder().profileId(profileId).build();
            dislikeRepository.saveAndFlush(dislike);

            dislikeList.put(dislike.getProfileId(), dislike);
            postEntity.setDislike(dislikeList);
        } else {
            dislikeList.remove(profileId);
            postEntity.setDislike(dislikeList);
        }
    }

    @Transactional
    @Override
    public void addOrRemoveDislikeOnComment(Map<Long, DislikeEntity> dislikeList, Long profileId, CommentEntity commentEntity) {
        if (dislikeList.get(profileId) == null) {
            DislikeEntity dislike = DislikeEntity.builder().profileId(profileId).build();
            dislikeRepository.saveAndFlush(dislike);

            dislikeList.put(dislike.getProfileId(), dislike);
            commentEntity.setDislike(dislikeList);
        } else {
            dislikeList.remove(profileId);
            commentEntity.setDislike(dislikeList);
        }
    }
}
