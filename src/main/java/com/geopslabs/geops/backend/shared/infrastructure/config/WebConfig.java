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
 * The allowed origin is configurable via the `frontend.url` property and
 * defaults to http://localhost:4200 for development.
 */
@Configuration
public class WebConfig {

    private final String frontendUrl;

    public WebConfig(@Value("${frontend.url:http://localhost:4200}") String frontendUrl) {
        this.frontendUrl = frontendUrl;
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // Allow only the configured frontend host in development. Adjust for production.
        config.setAllowedOrigins(Collections.singletonList(frontendUrl));

        // Use a mutable list for allowed methods to avoid NPE on add
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
        // Apply to all API endpoints
        source.registerCorsConfiguration("/api/**", config);
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
