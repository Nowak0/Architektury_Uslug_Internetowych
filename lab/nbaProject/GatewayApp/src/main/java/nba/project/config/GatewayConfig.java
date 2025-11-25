package nba.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("franchise", r -> r
                        .path("/api/franchises/**")
                        .uri("http://localhost:8083")
                )
                .route("player", r -> r
                        .path("/api/players/**")
                        .uri("http://localhost:8082")
                )
                .build();
    }
}
