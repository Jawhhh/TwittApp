package by.jawh.authmicroservice.controller;

import by.jawh.authmicroservice.business.dto.UserResponseDto;
import by.jawh.authmicroservice.jwt.JwtService;
import by.jawh.authmicroservice.business.service.UserServiceImpl;
import by.jawh.authmicroservice.business.dto.UserRequestLoginDto;
import by.jawh.authmicroservice.business.dto.UserRequestRegisterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserServiceImpl userService;
    private final JwtService jwtService;

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, UserRequestLoginDto userRequestLoginDto) {
        return ResponseEntity.ok().body(userService.updateUser(id, userRequestLoginDto));
    }

    @GetMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestParam("jwtToken") String jwtToken) {
        UserDetails userDetails = userService
                .userDetailsService()
                .loadUserByUsername(jwtService.extractUsername(jwtToken));

        return ResponseEntity.ok().body(jwtService.isTokenValid(jwtToken, userDetails));
    }

}
