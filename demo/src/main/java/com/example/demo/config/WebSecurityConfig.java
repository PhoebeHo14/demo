package com.example.demo.config;

import com.example.demo.filter.JWTAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/register", "/login"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                // 對請求進行驗證
                .authorizeRequests()
                // 所有/login的请求放行
                .antMatchers(AUTH_WHITELIST).permitAll()
                // ... 中間配置省略
                .and()
                // 添加過濾器，針對其他請求進行JWT的驗證
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
