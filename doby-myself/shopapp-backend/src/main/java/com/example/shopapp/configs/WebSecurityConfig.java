package com.example.shopapp.configs;

import com.example.shopapp.filters.JwtTokenFilter;
import com.example.shopapp.models.Role;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Value("${api.prefix}")
    private String apiPrefix;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(requests -> {
                requests
                    .requestMatchers(
                        String.format("%s/users/register", apiPrefix),
                        String.format("%s/users/login", apiPrefix)
                    )
                    .permitAll()
                    //categories
                    .requestMatchers(HttpMethod.GET,
                        String.format("%s/categories?**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)
                    .requestMatchers(HttpMethod.POST,
                        String.format("%s/categories/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                    .requestMatchers(HttpMethod.PUT,
                        String.format("%s/categories/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                    .requestMatchers(HttpMethod.DELETE,
                        String.format("%s/categories/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                    //products
                    .requestMatchers(HttpMethod.GET,
                        String.format("%s/products?**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)
                    .requestMatchers(HttpMethod.POST,
                        String.format("%s/products/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                    .requestMatchers(HttpMethod.PUT,
                        String.format("%s/products/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                    .requestMatchers(HttpMethod.DELETE,
                        String.format("%s/products/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                    //users

                    .requestMatchers(HttpMethod.GET,
                        String.format("%s/orders/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)
                    .requestMatchers(HttpMethod.POST,
                        String.format("%s/orders", apiPrefix)).hasAnyRole(Role.USER)
                    .requestMatchers(HttpMethod.PUT,
                        String.format("%s/orders/**", apiPrefix)).hasRole(Role.ADMIN)
                    .requestMatchers(HttpMethod.DELETE,
                        String.format("%s/orders/**", apiPrefix)).hasRole(Role.ADMIN)

                    //order_details
                    .requestMatchers(HttpMethod.GET,
                        String.format("%s/order_details?**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)
                    .requestMatchers(HttpMethod.POST,
                        String.format("%s/order_details/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                    .requestMatchers(HttpMethod.PUT,
                        String.format("%s/order_details/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                    .requestMatchers(HttpMethod.DELETE,
                        String.format("%s/order_details/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                    .anyRequest().authenticated()
                ;
            });

        http.cors(httpSecurityCorsConfigurer -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("http://localhost:4300/"));
            configuration.setAllowedMethods(List.of("GET", "POST","PATCH", "OPTIONS", "PUT", "DELETE"));
            configuration.setAllowedHeaders(List.of("authorization", "content-type", "x-auth-token"));
            configuration.setExposedHeaders(List.of("x-auth-token"));
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            httpSecurityCorsConfigurer.configurationSource(source);
        });

        return http.build();
    }
}
