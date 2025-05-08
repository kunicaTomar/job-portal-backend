package com.job_portal_backend.config;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

import com.job_portal_backend.security.CustomAccessDeniedHandler;
import com.job_portal_backend.security.JwtAuthenticationEntryPoint;
import com.job_portal_backend.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SpringSecurityConfig {
        @Autowired
        private final UserDetailsService userDetailsService;
        @Autowired
        private final JwtAuthenticationEntryPoint authenticationEntryPoint;
        @Autowired
        private final JwtAuthenticationFilter authenticationFilter;
        @Autowired
        private final CustomAccessDeniedHandler customAccessDeniedHandler;

        @Bean
        public static PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http.csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests((authorize) -> {

                                        // ========== PUBLIC ==========
                                        authorize
                                                        .requestMatchers("/api/auth/**").permitAll()
                                                        .requestMatchers("/api/job/**").permitAll();

                                        // ========== ADMIN ==========
                                        authorize
                                                        .requestMatchers("/api/admin/**").hasRole("ADMIN");

                                        // ========== RECRUITER ==========
                                        authorize
                                                        .requestMatchers("/api/recruiter/applications/**",
                                                                        "/api/recruiter/job/**")
                                                        .hasRole("RECRIUTER");

                                        // ========== USER ==========
                                        authorize
                                                        .requestMatchers("/api/user/applications/**").hasRole("USER");

                                        // ========== ADMIN & RECRUITER ==========
                                        authorize
                                                        .requestMatchers("/api/user/allUser")
                                                        .hasAnyRole("RECRIUTER","ADMIN");
                                        // ========== ADMIN & USER ==========
                                        authorize
                                                        .requestMatchers("/api/user/myProfile")
                                                        .hasAnyRole("ADMIN", "USER","RECRIUTER");

                                        // ========== DEFAULT ==========
                                        authorize.anyRequest().authenticated();
                                })
                                .httpBasic(Customizer.withDefaults());

                http.exceptionHandling(exception -> exception
                                .authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler)
                                );

                http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
                return configuration.getAuthenticationManager();
        }

}