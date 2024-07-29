package by.jawh.subscribeservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sub/profiles")
public class SubscribeController {


    @GetMapping("/current")
    public ResponseEntity<?> findForCurrentProfileId(@CookieValue("jwtToken") String token) {
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByProfileId(@PathVariable("id") Long profileId) {
        return ResponseEntity.ok().body("");
    }

    @PatchMapping("/{id}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable("id") Long profileId,
                                       @CookieValue("jwtToken") String token) {

        return ResponseEntity.ok().body("");
    }

    @PatchMapping("/{id}/unsubscribe")
    public ResponseEntity<?> unsubscribe(@PathVariable("id") Long profileId,
                                         @CookieValue("jwtToken") String token) {

        return ResponseEntity.ok().body("");
    }
}
