package main.hospital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        	// Desactivate for development
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            	// Authorize all request without authentication
                .anyRequest().permitAll()
            );

        return http.build();
    }
}
