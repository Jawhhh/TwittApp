package by.jawh.postservice.business.service;

import by.jawh.postservice.business.dto.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostResponseDto createPost(String text, MultipartFile picture, String token) throws Exception;

    PostResponseDto findByIdAndProfileId(Long profileId, Long id);

    Page<PostResponseDto> findAllByCurrentProfileId(String token, Pageable pageable);

    List<PostResponseDto> findAllByCurrentProfileId(String token);

    PostResponseDto findById(Long postId);

    PostResponseDto editPost(Long postId, String text, MultipartFile picture, String token) throws Exception;

    Boolean deletePost(Long postId, String token);

    PostResponseDto dislikeIt(Long id, String token);

    PostResponseDto likeIt(Long id, String token);

    Page<PostResponseDto> findAllByProfileId(Long profileId, Pageable pageable);

    List<PostResponseDto> findAllByProfileId(Long profileId);

    Page<PostResponseDto> findAll(Pageable pageable);

    List<PostResponseDto> findAll();

}
