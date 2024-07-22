package by.jawh.subscribeservice.controller;

import by.jawh.subscribeservice.business.service.impl.SubscribeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class SubscribeController {

    private final SubscribeServiceImpl subscribeService;

    @GetMapping("/current")
    public ResponseEntity<?> findForCurrentProfileId(@CookieValue("jwtToken") Long profileId) {
        return ResponseEntity.ok().body(subscribeService.findById(profileId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByProfileId(@PathVariable("id") Long profileId) {
        return ResponseEntity.ok().body(subscribeService.findById(profileId));
    }

    @PatchMapping("/{id}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable("id") Long profileId,
                                       @CookieValue("jwtToken") Long subscriberId) {

        return ResponseEntity.ok().body(subscribeService.subscribe(profileId, subscriberId));
    }

    @PatchMapping("/{id}/unsubscribe")
    public ResponseEntity<?> unsubscribe(@PathVariable("id") Long profileId,
                                         @CookieValue("jwtToken") Long unsubscriberId) {

        return ResponseEntity.ok().body(subscribeService.unsubscribe(profileId, unsubscriberId));
    }
}
