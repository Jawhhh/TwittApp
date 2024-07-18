package by.jawh.spring.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Component
@Order(HIGHEST_PRECEDENCE)
@Slf4j
public class SimpleCorsFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        log.info("зашли в корс фильтер");
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Headers", "*");
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Credentials", "true");

        log.info("set all headers");

        if (exchange.getRequest().getMethod().equals(HttpMethod.OPTIONS)) {
            exchange.getResponse().getHeaders().add("Access-Control-Max-Age", "3600");
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            log.info("return from options request");
            return Mono.empty();
        }

        log.info("return from cors filter");
        return chain.filter(exchange);
    }
}
