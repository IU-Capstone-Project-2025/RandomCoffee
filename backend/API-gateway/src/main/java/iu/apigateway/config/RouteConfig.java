package iu.apigateway.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Configuration
public class RouteConfig {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${profile-service.url}")
    private String profileServiceUrl;

    @Value("${mini-app.authorization.header-name}")
    private String authorizationHeaderName;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder
                .routes()
                .route(p -> p
                        .path("/profile")
                        .and()
                        .method(HttpMethod.POST, HttpMethod.PATCH, HttpMethod.GET)
                        .filters(f -> f
                                .filter(handleAuthorizationHeader())
                                .removeRequestHeader(authorizationHeaderName)
                        )
                        .uri(profileServiceUrl)
                )
                .route(p -> p
                        .path("/tag")
                        .and()
                        .method(HttpMethod.POST, HttpMethod.GET)
                        .uri(profileServiceUrl)
                )
                .route(p -> p
                        .path("/tag/*")
                        .and()
                        .method(HttpMethod.GET)
                        .uri(profileServiceUrl)
                )
                .route(p -> p
                        .path("/match-history/last-match")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f
                                .filter(handleAuthorizationHeader())
                                .removeRequestHeader(authorizationHeaderName)
                        )
                        .uri(profileServiceUrl)
                )
                .build();
    }

    private GatewayFilter handleAuthorizationHeader() {
        return (exchange, chain) -> {
            String authHeader = exchange
                    .getRequest()
                    .getHeaders()
                    .getFirst(authorizationHeaderName);

            String peerId = extractPeerIdFromAuthHeader(authHeader);
            if (peerId != null) {
                exchange = exchange.mutate()
                        .request(r -> r.header("peerId", peerId))
                        .build();
            }
            return chain.filter(exchange);
        };
    }

    private String extractPeerIdFromAuthHeader(String authHeader) {
        try {
            if (authHeader == null) {
                return null;
            }
            String decodedAuth = URLDecoder.decode(authHeader, StandardCharsets.UTF_8);
            Optional<String> userParam = Arrays.stream(decodedAuth.split("&"))
                    .filter(param -> param.startsWith("user="))
                    .findFirst();

            if (userParam.isPresent()) {
                String userJson = userParam.get().substring(5);
                JsonNode userNode = objectMapper.readTree(userJson);
                JsonNode idNode = userNode.get("id");

                if (idNode != null) {
                    return idNode.asText();
                }
            }
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
        }

        return null;
    }
}
