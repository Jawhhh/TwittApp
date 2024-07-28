package by.jawh.postservice.business.service.impl;

import by.jawh.postservice.business.service.DislikeService;
import by.jawh.postservice.common.entity.CommentEntity;
import by.jawh.postservice.common.entity.DislikeEntity;
import by.jawh.postservice.common.entity.LikeEntity;
import by.jawh.postservice.common.entity.PostEntity;
import by.jawh.postservice.common.repository.DislikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DislikeServiceImpl implements DislikeService {

    private final DislikeRepository dislikeRepository;

    @Transactional
    @Override
    public void addOrRemoveDislikeOnPost(List<DislikeEntity> dislikeList, Long profileId, PostEntity postEntity) {

        dislikeList.stream().forEach(dislike -> {
        if (Objects.equals(dislike.getProfileId(), profileId)) {
            dislikeList.remove(dislike);
            postEntity.setDislike(dislikeList);
        } else {
            DislikeEntity newLike = DislikeEntity.builder().profileId(profileId).build();
            dislikeRepository.saveAndFlush(newLike);
            dislikeList.add(newLike);
            postEntity.setDislike(dislikeList);
        }
    });
    }

    @Transactional
    @Override
    public void addOrRemoveDislikeOnComment(List<DislikeEntity> dislikeList, Long profileId, CommentEntity commentEntity) {

        dislikeList.stream().forEach(dislike -> {
            if (Objects.equals(dislike.getProfileId(), profileId)) {
                dislikeList.remove(dislike);
                commentEntity.setDislike(dislikeList);
            } else {
                DislikeEntity newLike = DislikeEntity.builder().profileId(profileId).build();
                dislikeRepository.saveAndFlush(newLike);
                dislikeList.add(newLike);
                commentEntity.setDislike(dislikeList);
            }
        });
    }
}
