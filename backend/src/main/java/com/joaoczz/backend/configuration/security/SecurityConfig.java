package com.joaoczz.backend.configuration.security;

import com.joaoczz.backend.configuration.filter.JwtTokenValidator;
import com.joaoczz.backend.service.implementation.UserDetailServiceimpl;
import com.joaoczz.backend.util.JWTUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JWTUtils jwtUtils;
    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(http -> {
                    // Swagger UI - público
                    http.requestMatchers("/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**").permitAll();

                    // Auth - público
                    http.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();

                    // GET públicos
                    http.requestMatchers(HttpMethod.GET, "/artists/**", "/genres/**", "/posts/**", "/comments/**").permitAll();
                    // Preview endpoint público
                    http.requestMatchers(HttpMethod.GET, "/api/preview", "/api/preview/**", "/preview", "/preview/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/users/{id}").permitAll();

                    // Perfil propio - autenticado
                    http.requestMatchers(HttpMethod.GET, "/users/me").authenticated();
                    http.requestMatchers(HttpMethod.DELETE, "/users/**").authenticated();

                    // Posts - autenticado
                    http.requestMatchers(HttpMethod.POST, "/posts/**").authenticated();
                    http.requestMatchers(HttpMethod.PUT, "/posts/**").authenticated();
                    http.requestMatchers(HttpMethod.DELETE, "/posts/**").authenticated();

                    // Comments - autenticado
                    http.requestMatchers(HttpMethod.POST, "/comments/**").authenticated();
                    http.requestMatchers(HttpMethod.DELETE, "/comments/**").authenticated();

                    // Likes - autenticado
                    http.requestMatchers(HttpMethod.POST, "/likes/**").authenticated();

                    // Artists y Genres - solo ADMIN
                    http.requestMatchers(HttpMethod.POST, "/artists/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/artists/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/artists/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/genres/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/genres/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/genres/**").hasRole("ADMIN");

                    // Users - Roles y Listado (ADMIN)
                    http.requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PATCH, "/users/*/roles").hasRole("ADMIN");
                    
                    // Users - Password (Autenticado)
                    http.requestMatchers(HttpMethod.PATCH, "/users/me/password").authenticated();

                    // Todo lo demás denegado
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceimpl userDetailServiceimpl) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailServiceimpl);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(origin -> !origin.isBlank())
                .collect(Collectors.toList()));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Auth-Token"));
        configuration.setExposedHeaders(List.of("X-Auth-Token"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
