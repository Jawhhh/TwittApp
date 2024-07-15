package by.jawh.authmicroservice.controller;

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

    @GetMapping("/test")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok().body("Get something from auth microservice");
    }

    @GetMapping("/{id}")
    public UserRequestRegisterDto findById(@PathVariable("id") Long id) {
        log.info("holla");
        return userService.findById(id);
    }

    @GetMapping
    public List<UserRequestRegisterDto> findAll() {
        return userService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, UserRequestLoginDto userRequestLoginDto) {
        return ResponseEntity.ok().body(userService.updateUser(id, userRequestLoginDto));
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestParam(name = "token") String jwtToken) {
        UserDetails userDetails = userService
                .userDetailsService()
                .loadUserByUsername(jwtService.extractUsername(jwtToken));

        //TODO exception handler
        return ResponseEntity.ok().body(jwtService.isTokenValid(jwtToken, userDetails));
    }

}
