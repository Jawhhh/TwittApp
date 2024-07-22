package by.jawh.postservice.business.service;

import by.jawh.postservice.common.entity.CommentEntity;
import by.jawh.postservice.common.entity.DislikeEntity;
import by.jawh.postservice.common.entity.PostEntity;

import java.util.Map;

public interface DislikeService {

    void addOrRemoveDislikeOnPost(Map<Long, DislikeEntity> dislikeList, Long profileId, PostEntity postEntity);

    void addOrRemoveDislikeOnComment(Map<Long, DislikeEntity> dislikeList, Long profileId, CommentEntity commentEntity);
}
