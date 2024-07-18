package by.jawh.authmicroservice.business.service;

import by.jawh.authmicroservice.jwt.JwtService;
import by.jawh.authmicroservice.business.dto.UserRequestLoginDto;
import by.jawh.authmicroservice.business.dto.UserRequestRegisterDto;
import by.jawh.authmicroservice.business.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserServiceImpl userService;
    private final UserMapper userMapper;


    public String signUp(UserRequestRegisterDto request) {

        userService.createUser(request);

        //TODO запрос в бд профилей для получение email по username

        return jwtService.generateToken(userMapper.registerDtoToEntity(request));
    }

    public String signIn(UserRequestLoginDto request) {

        if (!userService.login(request)) {
            throw new RuntimeException("Неправельные имя пользователя или пароль");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

         UserDetails userDetails = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        return jwtService.generateToken(userDetails);
    }

}
