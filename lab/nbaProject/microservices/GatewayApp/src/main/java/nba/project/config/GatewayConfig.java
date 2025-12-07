package nba.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    
    @Value("${franchise.app.url}")
    private String franchiseAppUrl;
    
    @Value("${player.app.url}")
    private String playerAppUrl;
    
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("franchise", r -> r
                        .path("/api/franchises/**")
                        .uri(franchiseAppUrl)
                )
                .route("player", r -> r
                        .path("/api/players/**")
                        .uri(playerAppUrl)
                )
                .build();
    }
}
