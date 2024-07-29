package by.jawh.postservice.controller;

import by.jawh.postservice.business.service.impl.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class CommentController {

    private final CommentServiceImpl commentService;

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<?> findByIdAndPostId(@PathVariable("postId") Long postId,
                                               @PathVariable("commentId") Long commentId) {

        return ResponseEntity.ok().body(commentService.findByIdAndPostId(commentId, postId));
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(commentService.findById(id));
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<?> findAllByPostId(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok().body(commentService.findAllByPostId(postId));
    }

    @GetMapping("/{postId}/comments/profiles")
    public ResponseEntity<?> findAllByProfileIdAndPostId(@PathVariable("postId") Long postId,
                                                @CookieValue("jwtToken") String token) {
        return ResponseEntity.ok().body(commentService.findAllByProfileIdAndPostId(postId, token));
    }

    @PostMapping("/{postId}/comments/create")
    public ResponseEntity<?> createComment(@PathVariable("postId") Long postId,
                                           @RequestParam(required = false) String text,
                                           @RequestParam(required = false) MultipartFile picture,
                                           @CookieValue("jwtToken") String token) {

        try {
            return ResponseEntity.ok().body(commentService.createComment(postId, text, picture, token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }

    @PatchMapping("/{postId}/comments/{commentId}/edit")
    public ResponseEntity<?> editComments(@PathVariable("postId") Long postId,
                                          @PathVariable("commentId") Long commentId,
                                          @RequestParam String text,
                                          @RequestParam MultipartFile picture,
                                          @CookieValue("jwtToken") String token) {

        try {
            return ResponseEntity.ok().body(commentService.editComment(postId, commentId, text, picture, token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/{postId}/comments/{commentId}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable("postId") Long postId,
                                           @PathVariable("commentId") Long commentId,
                                           @CookieValue("jwtToken") String token) {

        return ResponseEntity.ok().body(commentService.deleteComment(postId, commentId, token));
    }

    @PostMapping("/{postId}/comments/{commentId}/like")
    public ResponseEntity<?> likeIt(@PathVariable("postId") Long postId,
                                    @PathVariable("commentId") Long commentId,
                                    @CookieValue("jwtToken") String token) {

        return ResponseEntity.ok().body(commentService.likeIt(postId, commentId, token));
    }
}
