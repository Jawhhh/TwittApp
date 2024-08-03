package by.jawh.subscribeservice.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class AuthorizeService {

    private static final URI jwtExtractId = URI.create("http://auth:8081/auth/jwt/getId");
    private final RestTemplate restTemplate;

    public Long getProfileIdFromJwt(String token) {
        return restTemplate.postForEntity(jwtExtractId, token, Long.class).getBody();
    }
}
