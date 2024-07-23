package by.jawh.newsfeedservice.controller;

import by.jawh.newsfeedservice.business.service.NewsfeedService;
import by.jawh.newsfeedservice.common.entity.Post;
import by.jawh.newsfeedservice.common.repository.NewsfeedRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newsfeed")
@RequiredArgsConstructor
public class NewsfeedController {

    private final NewsfeedService newsfeedService;
    private final NewsfeedRedisRepository newsfeedRedisRepository;

    @GetMapping
    public ResponseEntity<Page<Post>> getNewsfeed(@RequestParam(required = false) Long profileId,
                                                  @RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer size,
                                                  @RequestParam(defaultValue = "timePublication") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        Page<Post> posts;

        if (profileId != null) {
            posts = newsfeedService.getPostByProfileId(profileId, pageable);
        } else {
            posts = newsfeedService.getAllPost(pageable);
        }

        return ResponseEntity.ok().body(posts);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save (@RequestParam("profileId") Long profileId,
                                   @RequestParam("id") Long id,
                                   @RequestParam(value = "text", required = false) String text,
                                   @RequestParam(value = "picture", required = false) String pictureUrl) {
        return ResponseEntity.ok().body(
                newsfeedRedisRepository.save(
                        Post.builder()
                                .id(id)
                                .profileId(profileId)
                                .text(text)
                                .build()
                )
        );
    }
}
