package com.gym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gym.security.CustomUserDetailsService;
import com.gym.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomUserDetailsService userDetailsService) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/auth/**")
                .permitAll()

                .requestMatchers(
                    "/",
                    "/index.html",
                    "/HTML/**",
                    "/CSS/**",
                    "/JAVASCRIPT/**")
                .permitAll()

                .requestMatchers(
                    HttpMethod.GET,
                    "/dashboard/me")
                .hasAnyRole("MEMBER", "ADMIN")

                .requestMatchers(
                    HttpMethod.GET,
                    "/dashboard/admin")
                .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.GET,
                    "/members/me")
                .hasAnyRole("MEMBER", "ADMIN")

                .requestMatchers(
                    HttpMethod.GET,
                    "/memberships/me")
                .hasAnyRole("MEMBER", "ADMIN")

                .requestMatchers(
                    HttpMethod.GET,
                    "/members/**")
                .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.POST,
                    "/members")
                .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/members/**")
                .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/members/**")
                .hasRole("ADMIN")
                
                .requestMatchers(
                    HttpMethod.GET,
                    "/memberships/**")
                .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.POST,
                    "/memberships/**")
                .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/memberships/**")
                .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/memberships/**")
                .hasRole("ADMIN")

                .anyRequest()
                .authenticated())

            .authenticationProvider(
                authenticationProvider())

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                        userDetailsService);

        provider.setPasswordEncoder(
                passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration
                .getAuthenticationManager();
    }
}