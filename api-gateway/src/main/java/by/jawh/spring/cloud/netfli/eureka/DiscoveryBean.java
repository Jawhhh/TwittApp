package by.jawh.spring.cloud.netfli.eureka;

//import org.springframework.cloud.netflix.eureka.RestTemplateTimeoutProperties;
//import org.springframework.cloud.netflix.eureka.http.DefaultEurekaClientHttpRequestFactorySupplier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.oauth2.client.*;
//import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
//
//import java.net.http.HttpHeaders;
//import java.util.List;
//
//public class DiscoveryBean {

//    @Bean
//    public DefaultEurekaClientHttpRequestFactorySupplier defaultEurekaClientHttpRequestFactorySupplier(
//            RestTemplateTimeoutProperties restTemplateTimeoutProperties,
//            ReactiveClientRegistrationRepository clientRegistrationRepository,
//            ReactiveOAuth2AuthorizedClientService authorizedClientService) {
//
//        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager =
//                new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
//                        clientRegistrationRepository, authorizedClientService);
//
//        return new DefaultEurekaClientHttpRequestFactorySupplier(restTemplateTimeoutProperties, List.of(
//                (request, entitty, context) -> {
//                    if (!request.containsHeader(HttpHeaders.AUTHORIZATION)) {
//                        OAuth2AuthorizedClient authorizedClient = authorizedClientManager
//                                .authorize(OAuth2AuthorizeRequest
//                                        .withClientRegistrationId("discovery")
//                                        .principal("api-gateway")
//                                        .build())
//                                .block();
//
//                    }
//                }
//        ));
//    }
//}
