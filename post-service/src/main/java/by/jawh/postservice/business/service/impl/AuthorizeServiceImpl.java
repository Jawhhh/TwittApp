package by.jawh.postservice.business.service.impl;

import by.jawh.postservice.common.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class AuthorizeServiceImpl {

    private static final URI jwtExtractId = URI.create(Constants.JWT_EXTRACT_ID);
    private final RestTemplate restTemplate;

    public Long getProfileIdFromJwt(String token) {
        return restTemplate.postForEntity(jwtExtractId, token, Long.class).getBody();
    }
}
