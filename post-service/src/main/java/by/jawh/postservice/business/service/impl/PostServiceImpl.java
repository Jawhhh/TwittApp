package by.jawh.postservice.business.service.impl;

import by.jawh.postservice.business.dto.PostRequestDto;
import by.jawh.postservice.business.dto.PostResponseDto;
import by.jawh.postservice.business.mapper.PostMapper;
import by.jawh.postservice.business.service.PostService;
import by.jawh.postservice.business.service.util.KafkaService;
import by.jawh.postservice.business.service.util.MinioService;
import by.jawh.postservice.common.entity.PostEntity;
import by.jawh.postservice.common.repository.PostRepository;
import by.jawh.postservice.exception.PostNotFoundException;
import by.jawh.postservice.exception.ProfileIdNotValidException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
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
    private final KafkaService kafkaService;


    @Transactional
    @Override
    public PostResponseDto createPost(String text, MultipartFile picture, String token) throws Exception {

        Long profileId = authorizeService.getProfileIdFromJwt(token);
        String bucket = "postPicture";
        String objectName = profileId + "/" + picture.getOriginalFilename();
        minioService.uploadFile(bucket, objectName, picture);

        PostEntity postEntity = PostEntity
                .builder()
                .text(text)
                .pictureUrl(minioService.getObjectUrl(bucket, objectName))
                .build();

        postEntity.setProfileId(profileId);
        postEntity.setTimePublication(LocalDateTime.now());
        postRepository.saveAndFlush(postEntity);

        kafkaService.sendNewsfeedEvent(postEntity);
        return postMapper.entityToResponseDto(postEntity);
    }

    @Override
    public PostResponseDto findById(Long id) {
        return postRepository.findById(id)
                .map(postMapper::entityToResponseDto)
                .orElseThrow(() ->
                        new PostNotFoundException("post with id: %s not found".formatted(id)));
    }

    @Override
    public List<PostResponseDto> findAllByProfileId(Long profileId) {
        return postRepository.findAllByProfileId(profileId).stream()
                .map(postMapper::entityToResponseDto)
                .toList();
    }

    @Transactional
    @Override
    public PostResponseDto editPost(Long id, String text, MultipartFile picture, String token) throws Exception {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() ->
                        new PostNotFoundException("post with id: %s not found".formatted(id)));

        Long profileId = authorizeService.getProfileIdFromJwt(token);
        if (profileId.equals(postEntity.getProfileId())) {

            PostRequestDto postRequestDto = new PostRequestDto();

            if (!text.isEmpty()) {
                postRequestDto.setText(text);
            }
            if (!picture.isEmpty()) {
                String bucket = "postPicture";
                String objectName = profileId + "/" + picture.getOriginalFilename();
                minioService.uploadFile(bucket, objectName, picture);
                postRequestDto.setPictureUrl(minioService.getObjectUrl(bucket, objectName));
            }

            postMapper.updatePostFromDto(postRequestDto, postEntity);
            postRepository.saveAndFlush(postEntity);

            kafkaService.sendNewsfeedEvent(postEntity);
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

        if (authorizeService.getProfileIdFromJwt(token).equals(postEntity.getProfileId())) {
            postRepository.delete(postEntity);
            postRepository.flush();
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
        return postMapper.entityToResponseDto(postEntity);
    }
}
