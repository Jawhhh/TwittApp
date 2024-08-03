package by.jawh.spring.config;

import by.jawh.spring.filter.JwtAuthenticationFilter;
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
                                           JwtAuthenticationFilter jwtAuthenticationFilter) {

        return builder.routes()
                .route("profile-service-route", r -> r.path("/profiles/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://profile-service"))
                .route("auth-service-route", r -> r.path("/auth/**")
                        .uri("lb://auth-service"))
                .route("auth-api-service-route", r -> r.path("/api/users/**")
                        .uri("lb://auth-service"))
                .route("post-service-route", r -> r.path("/posts/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://post-service"))
                .route("email-notification-service-route", r -> r.path("/mail/**")
                        .uri("lb://email-notification-service"))
                .route("subscribe-service-route", r -> r.path("/sub/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://subscribe-service"))
                .build();
    }
}
