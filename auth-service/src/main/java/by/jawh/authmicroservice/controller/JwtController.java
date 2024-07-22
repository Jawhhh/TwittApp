package by.jawh.authmicroservice.controller;

import by.jawh.authmicroservice.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth/jwt")
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/getId")
    public ResponseEntity<?> jwtGetId(@RequestParam("token") String token) {
        return ResponseEntity.ok().body(jwtService.extractId(token));
    }
}
