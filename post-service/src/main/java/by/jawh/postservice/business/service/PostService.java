package by.jawh.postservice.business.service;

import by.jawh.postservice.business.dto.PostResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostResponseDto createPost(String text, MultipartFile picture, String token) throws Exception;

    PostResponseDto findByIdAndProfileId(Long profileId, Long id);

    List<PostResponseDto> findAllByProfileId(Long id);

    List<PostResponseDto> findAllByCurrentProfileId(String token);

    PostResponseDto findById(Long postId);

    PostResponseDto editPost(Long postId, String text, MultipartFile picture, String token) throws Exception;

    Boolean deletePost(Long postId, String token);

    PostResponseDto dislikeIt(Long id, String token);

    PostResponseDto likeIt(Long id, String token);

}
