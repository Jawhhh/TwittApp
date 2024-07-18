package by.jawh.spring.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TtlService {

    public Long getTtl(String path) {
        return switch (path) {

            //TODO add new microservice route

            case "/profiles/**" -> TimeUnit.DAYS.toSeconds(1);
            case "/auth/**", "/api/users/**" -> 0L;
            default -> throw new RuntimeException("Wrong url");

        };
    }
}
