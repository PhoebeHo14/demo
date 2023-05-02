package com.example.demo.config;

import com.example.demo.filter.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/accounts/register").permitAll()
                .antMatchers("/accounts/login").permitAll()
                .antMatchers("/i18n/**").permitAll()
                .antMatchers("/api-docs.yaml").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
