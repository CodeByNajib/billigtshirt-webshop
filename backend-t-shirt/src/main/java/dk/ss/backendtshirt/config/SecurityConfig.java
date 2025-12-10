package dk.ss.backendtshirt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Vi slår CSRF helt fra for nemheds skyld i dev
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // Tillad alle API kald
                        .requestMatchers("/h2-console/**").permitAll() // Tillad H2
                        .requestMatchers("/error").permitAll() // Tillad standard fejl-sider
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // H2 fix
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Her linker vi til bean'en nedenunder!

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Vi bruger setAllowedOriginPatterns når allowCredentials er true
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:5500",
                "http://127.0.0.1:5500",
                "http://localhost:80",
                "http://127.0.0.1:80"
        ));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Anvend denne konfiguration på ALLE endpoints, ikke kun /api/**
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}