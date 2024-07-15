package by.jawh.spring.config;

import by.jawh.spring.Security.JwtAuthenticationFilter;
import by.jawh.spring.Security.SimpleCorsFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                           JwtAuthenticationFilter jwtAuthenticationFilter,
                                           SimpleCorsFilter simpleCorsFilter) {

        return builder.routes()
                .route("profile-service-route", r -> r.path("/profiles/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://profile-service"))
                .route("auth-service-route", r -> r.path("/auth/**")
                        .uri("lb://auth-service"))
                .route("auth-api-service-route", r -> r.path("/api/users/**")
                        .uri("lb://api-users/**"))
                .build();
    }
}
