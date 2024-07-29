package by.jawh.postservice.business.service.impl;

import by.jawh.postservice.business.dto.CommentRequestDto;
import by.jawh.postservice.business.dto.CommentResponseDto;
import by.jawh.postservice.business.dto.PostResponseDto;
import by.jawh.postservice.business.mapper.CommentMapper;
import by.jawh.postservice.business.mapper.PostMapper;
import by.jawh.postservice.business.service.CommentService;
import by.jawh.postservice.business.service.util.MinioService;
import by.jawh.postservice.business.service.util.RedisService;
import by.jawh.postservice.common.entity.CommentEntity;
import by.jawh.postservice.common.entity.PostEntity;
import by.jawh.postservice.common.repository.CommentRepository;
import by.jawh.postservice.common.repository.PostRepository;
import by.jawh.postservice.common.util.Constants;
import by.jawh.postservice.exception.BlankPostException;
import by.jawh.postservice.exception.CommentNotFoundException;
import by.jawh.postservice.exception.ProfileIdNotValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AuthorizeServiceImpl authorizeService;
    private final LikeServiceImpl likeService;
    private final MinioService minioService;
    private final PostServiceImpl postService;
    private final PostMapper postMapper;
    private final RedisService redisService;
    private final PostRepository postRepository;

    @Transactional
    @Override
    public CommentResponseDto createComment(Long postId, String text, MultipartFile picture, String token) throws Exception {

        if (text == null && picture == null) {
            throw new BlankPostException("post can't be blank. Write something or choose picture");
        }

        PostEntity postEntity = postMapper.responseDtoToEntity(postService.findById(postId));
        Long profileId = authorizeService.getProfileIdFromJwt(token);
        CommentEntity commentEntity = new CommentEntity();

        if (picture != null) {
            String bucket = "commentPicture";
            String objectName = profileId + "/" + picture.getOriginalFilename();
            minioService.uploadFile(bucket, objectName, picture);
            commentEntity.setPictureUrl(minioService.getObjectUrl(bucket, objectName));
        }

        if(text != null) {
            commentEntity.setText(text);
        }

        commentEntity.setProfileId(profileId);
        commentEntity.setTimePublication(LocalDateTime.now());
        commentEntity.setPost(postEntity);
        commentRepository.saveAndFlush(commentEntity);

        Set<CommentEntity> comment = postEntity.getComment();
        comment.add(commentEntity);
        postRepository.saveAndFlush(postEntity);

        redisService.save(Constants.PREFIX_CACHE_KEY_FOR_POST + profileId, postEntity.getId(), postEntity, Constants.TTL_FOR_POST);
        log.info("create new comment with profile id: %s and post: %s".formatted(profileId, postId));
        return commentMapper.entityToResponseDto(commentEntity);
    }

    @Override
    public CommentResponseDto findById(Long id) {
        return commentRepository.findById(id)
                .map(commentMapper::entityToResponseDto)
                .orElseThrow(() ->
                        new CommentNotFoundException("comment with id: %s not found".formatted(id)));
    }

    @Override
    public CommentResponseDto findByIdAndPostId(Long id, Long postId) {
        return commentRepository.findByIdAndPostId(id, postId)
                .map(commentMapper::entityToResponseDto)
                .orElseThrow(() ->
                        new CommentNotFoundException("comment with id: %s and post id: %s not found".formatted(id, postId)));
    }

    @Override
    public List<CommentResponseDto> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId).stream()
                .map(commentMapper::entityToResponseDto)
                .toList();
    }

    @Override
    public List<CommentResponseDto> findAllByProfileIdAndPostId(Long postId, String token) {
        Long profileId = authorizeService.getProfileIdFromJwt(token);

        return commentRepository.findAllByProfileIdAndPostId(postId, profileId).stream()
                .map(commentMapper::entityToResponseDto)
                .toList();
    }

    @Transactional
    @Override
    public Boolean deleteComment(Long postId, Long id, String token) {

        Long profileId = authorizeService.getProfileIdFromJwt(token);
        CommentEntity commentEntity = commentRepository.findByIdAndPostId(id, postId)
                .orElseThrow(() ->
                        new CommentNotFoundException("comment with id: %s not found".formatted(id)));

        if (commentEntity.getProfileId().equals(profileId)) {
            commentRepository.delete(commentEntity);
            commentRepository.flush();
            log.info("comment with id: %s on post: %s was deleted".formatted(id, postId));
            return true;
        } else {
            throw new ProfileIdNotValidException("you can't delete this comment with current profile id");
        }
    }

    @Transactional
    @Override
    public CommentResponseDto editComment(Long postId, Long id, String text, MultipartFile picture, String token) throws Exception {

        Long profileId = authorizeService.getProfileIdFromJwt(token);

        CommentEntity commentEntity = commentRepository.findByIdAndPostId(id, postId)
                .orElseThrow(() ->
                        new CommentNotFoundException("comment with id: %s not found".formatted(id)));

        if (commentEntity.getProfileId().equals(profileId)) {

            CommentRequestDto commentRequestDto = new CommentRequestDto();
            commentRequestDto.setText(text);

            if(!picture.isEmpty()) {
                String bucket = "commentPicture";
                String objectName = profileId + "/" + picture.getOriginalFilename();
                minioService.uploadFile(bucket, objectName, picture);
                commentRequestDto.setPictureUrl(minioService.getObjectUrl(bucket, objectName));
            }

            commentMapper.updateEntityFromDto(commentRequestDto, commentEntity);
            commentRepository.flush();
            log.info("comment with id: %s on post: %s was changed".formatted(id, postId));
            return commentMapper.entityToResponseDto(commentEntity);
        } else {
            throw new ProfileIdNotValidException("you can't edit this comment with current profile id");
        }
    }

    @Override
    public CommentResponseDto likeIt(Long postId, Long id, String token) {

        Long profileId = authorizeService.getProfileIdFromJwt(token);

        CommentEntity commentEntity = commentRepository.findByIdAndPostId(id, postId)
                .orElseThrow(() ->
                        new CommentNotFoundException("comment with id: %s and post id: %s not found".formatted(id, postId)));

        likeService.addOrRemoveLikeOnComment(commentEntity.getLike(), profileId, commentEntity);
        commentRepository.saveAndFlush(commentEntity);
        return commentMapper.entityToResponseDto(commentEntity);
    }
}
