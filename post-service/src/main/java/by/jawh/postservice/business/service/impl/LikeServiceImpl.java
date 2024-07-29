package by.jawh.postservice.business.service.impl;

import by.jawh.postservice.business.service.LikeService;
import by.jawh.postservice.common.entity.CommentEntity;
import by.jawh.postservice.common.entity.CommentLikeEntity;
import by.jawh.postservice.common.entity.PostEntity;
import by.jawh.postservice.common.entity.PostLikeEntity;
import by.jawh.postservice.common.repository.CommentLikeRepository;
import by.jawh.postservice.common.repository.CommentRepository;
import by.jawh.postservice.common.repository.PostLikeRepository;
import by.jawh.postservice.common.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public void addOrRemoveLikeOnPost(Set<PostLikeEntity> likeList, Long profileId, PostEntity postEntity) {

        boolean likeRemoved = false;

        for (Iterator<PostLikeEntity> iterator = likeList.iterator(); iterator.hasNext(); ) {
            PostLikeEntity like = iterator.next();
            if (Objects.equals(like.getProfileId(), profileId)) {
                iterator.remove();
                likeRemoved = true;
                break;
            }
        }

        if (likeRemoved) {
            postRepository.saveAndFlush(postEntity);
            return;
        }


        PostLikeEntity newLike = PostLikeEntity.builder().profileId(profileId).post(postEntity).build();
        postLikeRepository.saveAndFlush(newLike);
        likeList.add(newLike);
        postEntity.setLike(likeList);
        postRepository.saveAndFlush(postEntity);
    }

    @Transactional
    @Override
    public void addOrRemoveLikeOnComment(List<CommentLikeEntity> likeList, Long profileId, CommentEntity commentEntity) {

        boolean likeRemoved = false;

        for (Iterator<CommentLikeEntity> iterator = likeList.iterator(); iterator.hasNext(); ) {
            CommentLikeEntity like = iterator.next();
            if (Objects.equals(like.getProfileId(), profileId)) {
                iterator.remove();
                likeRemoved = true;
                break;
            }
        }

        if (likeRemoved) {
            commentRepository.saveAndFlush(commentEntity);
            return;
        }


        CommentLikeEntity newLike = CommentLikeEntity.builder().profileId(profileId).comment(commentEntity).build();
        commentLikeRepository.saveAndFlush(newLike);
        likeList.add(newLike);
        commentEntity.setLike(likeList);
        commentRepository.saveAndFlush(commentEntity);
    }
}

