package by.jawh.postservice.business.service.impl;

import by.jawh.postservice.business.dto.PostResponseDto;
import by.jawh.postservice.business.mapper.PostMapper;
import by.jawh.postservice.business.service.PostService;
import by.jawh.postservice.business.service.util.RedisService;
import by.jawh.postservice.business.service.util.MinioService;
import by.jawh.postservice.common.util.Constants;
import by.jawh.postservice.common.entity.PostEntity;
import by.jawh.postservice.common.repository.PostRepository;
import by.jawh.postservice.exception.BlankPostException;
import by.jawh.postservice.exception.PostNotFoundException;
import by.jawh.postservice.exception.ProfileIdNotValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final AuthorizeServiceImpl authorizeService;
    private final LikeServiceImpl likeService;
    private final DislikeServiceImpl dislikeService;
    private final MinioService minioService;
    private final RedisService redisService;


    @Transactional
    @Override
    public PostResponseDto createPost(String text, MultipartFile picture, String token) throws Exception {

        if (text == null && picture == null) {
            throw new BlankPostException("post can't be blank. Write something or choose picture");
        }

        Long profileId = authorizeService.getProfileIdFromJwt(token);
        PostEntity postEntity = new PostEntity();

        if (picture != null) {
            String bucket = "postPicture";
            String objectName = profileId + "/" + picture.getOriginalFilename();
            minioService.uploadFile(bucket, objectName, picture);
            postEntity.setPictureUrl(minioService.getObjectUrl(bucket, objectName));
        }

        if (text != null) {
            postEntity.setText(text);
        }

        postEntity.setProfileId(profileId);
        postEntity.setTimePublication(LocalDateTime.now());
        postRepository.saveAndFlush(postEntity);

        redisService.save(Constants.PREFIX_CACHE_KEY_FOR_POST + profileId, postEntity.getId(), postEntity, Constants.TTL_FOR_POST);
        return postMapper.entityToResponseDto(postEntity);
    }

    @Override
    public PostResponseDto findByIdAndProfileId(Long profileId, Long id) {

        PostEntity byPostId = redisService.findByPostId(Constants.PREFIX_CACHE_KEY_FOR_POST + profileId, id);

        if (byPostId != null) {
            return postMapper.entityToResponseDto(byPostId);
        } else {
            PostEntity postEntity = postRepository.findById(id)
                    .orElseThrow(() ->
                            new PostNotFoundException("post with id: %s not found".formatted(id)));
        redisService.save(Constants.PREFIX_CACHE_KEY_FOR_POST + profileId, id, postEntity, Constants.TTL_FOR_POST);
        return postMapper.entityToResponseDto(postEntity);
        }
    }

    //* not support redis cache, only postgres :(
    @Override
    public PostResponseDto findById(Long postId) {
        return postRepository.findById(postId)
                .map(postMapper::entityToResponseDto)
                .orElseThrow(() ->
                        new PostNotFoundException("post with id: %s not found".formatted(postId)));
    }

    @Override
    public List<PostResponseDto> findAllByProfileId(Long profileId) {

        List<PostEntity> allPostByProfileId = redisService.findAllPostByProfileId(Constants.PREFIX_CACHE_KEY_FOR_POST + profileId);

        if (!allPostByProfileId.isEmpty()) {
             return allPostByProfileId.stream()
                     .map(postMapper::entityToResponseDto)
                     .toList();
         } else {
            List<PostEntity> postsByProfileId = postRepository.findAllByProfileId(profileId);
            redisService.saveAll(Constants.PREFIX_CACHE_KEY_FOR_POST + profileId, postsByProfileId, Constants.TTL_FOR_POST);
            return postsByProfileId.stream()
                    .map(postMapper::entityToResponseDto)
                    .toList();
        }
    }

    @Override
    public List<PostResponseDto> findAllByCurrentProfileId(String token) {

        Long profileId = authorizeService.getProfileIdFromJwt(token);
        List<PostEntity> allPostByProfileId = redisService.findAllPostByProfileId(Constants.PREFIX_CACHE_KEY_FOR_POST + profileId);

        if (!allPostByProfileId.isEmpty()) {
            return allPostByProfileId.stream()
                    .map(postMapper::entityToResponseDto)
                    .toList();
        } else {
            List<PostEntity> postsByProfileId = postRepository.findAllByProfileId(profileId);
            redisService.saveAll(Constants.PREFIX_CACHE_KEY_FOR_POST + profileId, postsByProfileId, Constants.TTL_FOR_POST);
            return postsByProfileId.stream()
                    .map(postMapper::entityToResponseDto)
                    .toList();
        }
    }

    @Transactional
    @Override
    public PostResponseDto editPost(Long id, String text, MultipartFile picture, String token) throws Exception {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() ->
                        new PostNotFoundException("post with id: %s not found".formatted(id)));

        Long profileId = authorizeService.getProfileIdFromJwt(token);
        if (profileId.equals(postEntity.getProfileId())) {

            if (!text.isEmpty()) {
                postEntity.setText(text);
            }
            if (!picture.isEmpty()) {
                String bucket = "postPicture";
                String objectName = profileId + "/" + picture.getOriginalFilename();
                minioService.uploadFile(bucket, objectName, picture);
                postEntity.setPictureUrl(minioService.getObjectUrl(bucket, objectName));
            }

            postRepository.saveAndFlush(postEntity);

            redisService.save(Constants.PREFIX_CACHE_KEY_FOR_POST + profileId, postEntity.getId(), postEntity, Constants.TTL_FOR_POST);
            return postMapper.entityToResponseDto(postEntity);
        } else {
            throw new ProfileIdNotValidException("you can't edit this post with current profile id");
        }
    }

    @Transactional
    @Override
    public Boolean deletePost(Long id, String token) {

        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() ->
                        new PostNotFoundException("post with id: %s not found".formatted(id)));

        Long profileId = authorizeService.getProfileIdFromJwt(token);

        if (profileId.equals(postEntity.getProfileId())) {
            postRepository.delete(postEntity);
            postRepository.flush();
            redisService.delete(Constants.PREFIX_CACHE_KEY_FOR_POST + profileId, postEntity.getId());
            return true;
        } else {
            throw new ProfileIdNotValidException("you can't delete this post with current profile id");
        }
    }

    @Transactional
    @Override
    public PostResponseDto likeIt(Long id, String token) {

        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() ->
                        new PostNotFoundException("post with id: %s not found".formatted(id)));

        Long profileId = authorizeService.getProfileIdFromJwt(token);

        likeService.addOrRemoveLikeOnPost(postEntity.getLike(), profileId, postEntity);
        postRepository.flush();
        redisService.save(Constants.PREFIX_CACHE_KEY_FOR_POST + profileId, postEntity.getId(), postEntity, Constants.TTL_FOR_POST);
        return postMapper.entityToResponseDto(postEntity);
    }

    @Transactional
    @Override
    public PostResponseDto dislikeIt(Long id, String token) {

        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() ->
                        new PostNotFoundException("post with id: %s not found".formatted(id)));

        Long profileId = authorizeService.getProfileIdFromJwt(token);

        dislikeService.addOrRemoveDislikeOnPost(postEntity.getDislike(), profileId, postEntity);
        postRepository.flush();
        redisService.save(Constants.PREFIX_CACHE_KEY_FOR_POST + profileId, postEntity.getId(), postEntity, Constants.TTL_FOR_POST);
        return postMapper.entityToResponseDto(postEntity);
    }
}
