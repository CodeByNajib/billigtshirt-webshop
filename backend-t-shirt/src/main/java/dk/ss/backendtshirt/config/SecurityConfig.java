package dk.ss.backendtshirt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF fra i dev/API context
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/**").permitAll()       // Åben for API endpoints
                    .requestMatchers("/error").permitAll()        // Åben for fejl-beskeder
                    .anyRequest().authenticated()                 // Kræv login på alt andet (lukker døren igen)
            )
            .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Aktiverer vores CORS config nedenfor

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
//        // Dine specifikke tilladte origins (Frontend)
//        configuration.setAllowedOriginPatterns(List.of(
//                "http://localhost:5500",
//                "http://127.0.0.1:5500",
//                "http://localhost:80",
//                "http://127.0.0.1:80"
//        ));
        configuration.setAllowedOriginPatterns(List.of("*"));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}