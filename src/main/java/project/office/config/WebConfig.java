package project.office.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.office.properties.CorsProperties;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private CorsProperties corsProperties;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all paths
                .allowedOrigins(corsProperties.getAllowedOrigins()) // Specific origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH") // Allowed HTTP methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true) // Allow sending cookies and authentication headers
                .allowedOriginPatterns("/**")
                .maxAge(3600); // Preflight request cache duration
    }
}