package project.office.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import project.office.model.entity.AppUser;

import java.util.Optional;

@Configuration
public class AuditorConfig {
    @Bean
    public AuditorAware<AppUser> auditor() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(p -> p instanceof AppUser ? (AppUser) p : null);
    }
}