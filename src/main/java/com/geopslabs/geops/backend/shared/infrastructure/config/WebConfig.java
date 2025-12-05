package com.geopslabs.geops.backend.shared.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Global CORS configuration
 *
 * This configuration enables Cross-Origin requests from the frontend development
 * server and allows common HTTP methods and headers used by the application.
 * The allowed origins are configurable via the `frontend.url` and
 * `prod.frontend.url` properties.
 */
@Configuration
public class WebConfig {

    private final List<String> allowedOrigins;

    public WebConfig(
            @Value("${frontend.url:http://localhost:4200}") String frontendUrl,
            @Value("${prod.frontend.url:}") String prodFrontendUrl,
            @Value("${prod.backend.url:}") String prodBackendUrl
    ) {
        List<String> origins = new ArrayList<>();
        if (frontendUrl != null && !frontendUrl.isBlank()) {
            origins.add(frontendUrl);
        }
        if (prodFrontendUrl != null && !prodFrontendUrl.isBlank()) {
            origins.add(prodFrontendUrl);
        }
        if (prodBackendUrl != null && !prodBackendUrl.isBlank()) {
            origins.add(prodBackendUrl);
        }
        this.allowedOrigins = Collections.unmodifiableList(origins);
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // Use configured allowed origins (dev and optional prod)
        config.setAllowedOrigins(this.allowedOrigins);

        List<String> methods = new ArrayList<>();
        methods.add("GET");
        methods.add("POST");
        methods.add("PUT");
        methods.add("PATCH");
        methods.add("DELETE");
        methods.add("OPTIONS");
        config.setAllowedMethods(methods);

        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setExposedHeaders(Collections.singletonList("Location"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
