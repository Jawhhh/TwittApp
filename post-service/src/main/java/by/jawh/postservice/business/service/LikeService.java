package by.jawh.postservice.business.service;

import by.jawh.postservice.common.entity.CommentEntity;
import by.jawh.postservice.common.entity.LikeEntity;
import by.jawh.postservice.common.entity.PostEntity;

import java.util.Map;

public interface LikeService {

    void addOrRemoveLikeOnPost(Map<Long, LikeEntity> likeList, Long profileId, PostEntity postEntity);

    void addOrRemoveLikeOnComment(Map<Long, LikeEntity> likeList, Long profileId, CommentEntity commentEntity);
}
