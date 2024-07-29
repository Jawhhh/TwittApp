package by.jawh.postservice.business.service;

import by.jawh.postservice.business.dto.CommentResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommentService {

    CommentResponseDto createComment(Long postId, String text, MultipartFile picture, String token) throws Exception;

    CommentResponseDto findById(Long id);

    CommentResponseDto findByIdAndPostId(Long id, Long postId);

    List<CommentResponseDto> findAllByPostId(Long postId);

    List<CommentResponseDto> findAllByProfileIdAndPostId(Long postId, String token);

    Boolean deleteComment(Long postId, Long id, String token);

    CommentResponseDto editComment(Long postId, Long id, String text, MultipartFile picture, String token) throws Exception;

    CommentResponseDto likeIt(Long postId, Long id, String token);

}
