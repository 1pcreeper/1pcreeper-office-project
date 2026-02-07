package project.office.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.office.filter.JwtAuthenticationFilter;
import project.office.model.entity.enums.AppUserRole;
import project.office.properties.CorsProperties;
import project.office.util.JwtUtil;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CorsProperties corsProperties;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Apply the CORS configuration
                .csrf((AbstractHttpConfigurer::disable))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/admin/users/**").hasAnyAuthority(
                                AppUserRole.ADMIN.name()
                        )
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins())); // Allow specific origins
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")); // Allow HTTP methods
        configuration.setAllowedHeaders(List.of("*")); // Allow all headers
        configuration.setAllowCredentials(true); // Allow cookies or Authorization headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply to all paths
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    @Profile("dev")
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers("/swagger-ui/**", "/v3/**");
    }
}
