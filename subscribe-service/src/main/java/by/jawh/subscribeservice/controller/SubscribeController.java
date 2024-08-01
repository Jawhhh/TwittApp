package by.jawh.subscribeservice.controller;

import by.jawh.subscribeservice.business.service.impl.SubscribeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sub/profiles")
public class SubscribeController {

    private final SubscribeServiceImpl subscribeService;

    @GetMapping("/current")
    public ResponseEntity<?> findForCurrentProfileId(@CookieValue("jwtToken") String token) {
        return ResponseEntity.ok().body(subscribeService.findByCurrentUser(token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByProfileId(@PathVariable("id") Long profileId) {
        return ResponseEntity.ok().body(subscribeService.findById(profileId));
    }

    @PatchMapping("/{id}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable("id") Long publisherId,
                                       @CookieValue("jwtToken") String token) {

        return ResponseEntity.ok().body(subscribeService.subscribe(token, publisherId));
    }

    @PatchMapping("/{id}/unsubscribe")
    public ResponseEntity<?> unsubscribe(@PathVariable("id") Long publisherId,
                                         @CookieValue("jwtToken") String token) {

        return ResponseEntity.ok().body(subscribeService.unsubscribe(token, publisherId));
    }
}
