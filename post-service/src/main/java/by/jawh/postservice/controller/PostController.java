package by.jawh.postservice.controller;

import by.jawh.postservice.business.service.impl.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostServiceImpl postService;

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(postService.findById(id));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {

        return ResponseEntity.ok().body(postService.findAll());
    }

    @GetMapping("/{profileId}/{postId}")
    public ResponseEntity<?> findByProfileIdAndPostId(@PathVariable("profileId") Long profileId,
                                                      @PathVariable("postId") Long postId) {
        return ResponseEntity.ok().body(postService.findByIdAndProfileId(profileId, postId));
    }

    @GetMapping("/me")
    public ResponseEntity<?> findAllPostByCurrentProfile(@CookieValue("jwtToken") String token) {

        return ResponseEntity.ok().body(postService.findAllByCurrentProfileId(token));
    }

    @GetMapping("/profiles/{profileId}")
    public ResponseEntity<?> findAllByProfileId(@PathVariable("profileId") Long profileId) {

        return ResponseEntity.ok().body(postService.findAllByProfileId(profileId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestParam(required = false) String text,
                                        @RequestParam(required = false) MultipartFile picture,
                                        @CookieValue("jwtToken") String token) {

        try {
            return ResponseEntity.ok().body(postService.createPost(text, picture, token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id, @CookieValue("jwtToken") String token) {
        return ResponseEntity.ok().body(postService.deletePost(id, token));
    }

    @PatchMapping("edit/{id}")
    public ResponseEntity<?> editPost(@PathVariable("id") Long id,
                                      @RequestParam String text,
                                      @RequestParam MultipartFile picture,
                                      @CookieValue("jwtToken") String token) {

        try {
            return ResponseEntity.ok().body(postService.editPost(id, text, picture, token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> likeIt(@PathVariable("id") Long id, @CookieValue("jwtToken") String token) {
        return ResponseEntity.ok().body(postService.likeIt(id, token));
    }
}
