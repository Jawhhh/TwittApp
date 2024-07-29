package by.jawh.postservice.business.service;

import by.jawh.postservice.common.entity.CommentEntity;
import by.jawh.postservice.common.entity.CommentLikeEntity;
import by.jawh.postservice.common.entity.PostEntity;
import by.jawh.postservice.common.entity.PostLikeEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface LikeService {

    void addOrRemoveLikeOnPost(Set<PostLikeEntity> likeList, Long profileId, PostEntity postEntity);

    void addOrRemoveLikeOnComment(List<CommentLikeEntity> likeList, Long profileId, CommentEntity commentEntity);
}
