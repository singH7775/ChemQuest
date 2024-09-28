package com.ChemQuest.ChemQuest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.ChemQuest.ChemQuest.service.CustomStudentDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final CustomStudentDetailsService studentDetailsService;
    private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(CustomStudentDetailsService studentDetailsService, 
                          JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.studentDetailsService = studentDetailsService;
        this.jwtAuthenticationSuccessHandler = jwtAuthenticationSuccessHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/signin", "/signup", "/addUser").permitAll()
                .requestMatchers("/static/**", "/images/**", "/css/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/signin")
                .loginProcessingUrl("/authenticate")
                .failureUrl("/signin?error=true")
                .successHandler(jwtAuthenticationSuccessHandler)
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "JWT")
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .userDetailsService(studentDetailsService);
        
        return http.build();
    }
}