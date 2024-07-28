package by.jawh.postservice.business.service;

import by.jawh.postservice.common.entity.CommentEntity;
import by.jawh.postservice.common.entity.LikeEntity;
import by.jawh.postservice.common.entity.PostEntity;

import java.util.List;
import java.util.Map;

public interface LikeService {

    void addOrRemoveLikeOnPost(List<LikeEntity> likeList, Long profileId, PostEntity postEntity);

    void addOrRemoveLikeOnComment(List<LikeEntity> likeList, Long profileId, CommentEntity commentEntity);
}
