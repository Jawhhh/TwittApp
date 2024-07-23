package by.jawh.authmicroservice.business.service;

import by.jawh.authmicroservice.common.entity.UserEntity;
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

        UserEntity user = userService.createUser(request);

        return jwtService.generateToken(user);
    }

    public String signIn(UserRequestLoginDto request) {

        userService.login(request);


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
