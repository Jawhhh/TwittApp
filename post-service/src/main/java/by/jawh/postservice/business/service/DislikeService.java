package by.jawh.postservice.business.service;

import by.jawh.postservice.common.entity.CommentEntity;
import by.jawh.postservice.common.entity.DislikeEntity;
import by.jawh.postservice.common.entity.PostEntity;

import java.util.List;
import java.util.Map;

public interface DislikeService {

    void addOrRemoveDislikeOnPost(List<DislikeEntity> dislikeList, Long profileId, PostEntity postEntity);

    void addOrRemoveDislikeOnComment(List<DislikeEntity> dislikeList, Long profileId, CommentEntity commentEntity);
}
