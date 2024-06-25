package rest.microservices.eurekaservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("http://eureka-service:8761/?continue")
                        .authenticated()
                        .requestMatchers("http://eureka-service:8761")
                        .authenticated()
                        .anyRequest()
                        .permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
