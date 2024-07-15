package by.jawh.spring.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GatewayFilter {

    private final RestTemplate restTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        HttpCookie jwtCookie = exchange.getRequest().getCookies().getFirst("Authentication");

        if (jwtCookie == null) {
            return redirectToLogin(exchange);
        }

        String jwtToken = jwtCookie.getValue();

        ResponseEntity<Boolean> response = checkValidJwtToken(jwtToken);

        if (response.getStatusCode() == HttpStatus.OK && Boolean.TRUE.equals(response.getBody())) {
            return chain.filter(exchange);
        } else {
            return redirectToLogin(exchange);
        }
    }

    public Mono<Void> redirectToLogin(ServerWebExchange exchange) {
        URI uri = URI.create("http://127.0.0.1:5500/login_dir/index.html");

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SEE_OTHER);
        response.getHeaders().setLocation(uri);

        return response.setComplete();
    }

    public ResponseEntity<Boolean> checkValidJwtToken(String jwtToken) {
        URI url;
        try {
            url = new URI("http://localhost:8080/api/users/validateToken?token=" + jwtToken);
        }catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return restTemplate.exchange(
                new RequestEntity<>(HttpMethod.POST, url),
                Boolean.class);
    }
}
