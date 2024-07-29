package by.jawh.authmicroservice.controller;

import by.jawh.authmicroservice.business.service.AuthenticationService;
import by.jawh.authmicroservice.business.dto.UserRequestLoginDto;
import by.jawh.authmicroservice.business.dto.UserRequestRegisterDto;
import by.jawh.authmicroservice.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/registration")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserRequestRegisterDto dto, HttpServletResponse response) {

        String jwt = authenticationService.signUp(dto);
        Cookie cookie = new Cookie("jwtToken", jwt);

        cookie.setHttpOnly(true);
        cookie.setMaxAge(24 * 60 * 60); // 1 день
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok().body(jwt);

    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody @Valid UserRequestLoginDto dto, HttpServletResponse response) {
        String jwt = authenticationService.signIn(dto);
        Cookie cookie = new Cookie("jwtToken", jwt);

        cookie.setHttpOnly(true);
        cookie.setMaxAge(24 * 60 * 60); // 1 день
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok().body(jwt);
    }

    @PostMapping("/jwt/getId")
    public ResponseEntity<?> extractId(@RequestBody String jwtToken) {
        return ResponseEntity.ok().body(jwtService.extractId(jwtToken));
    }
}
