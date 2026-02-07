package project.office.properties;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
    private String allowedOrigins;

    public String[] getAllowedOrigins() {
        return allowedOrigins.split(",");
    }
}
