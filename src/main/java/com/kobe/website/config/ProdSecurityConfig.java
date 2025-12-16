package com.kobe.website.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * packageName    : com.kobe.website.config
 * fileName       : ProdSecurityConfig
 * author         : kobe
 * date           : 2025. 12. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 15.        kobe       최초 생성
 */
@Profile("prod")
@Configuration
@EnableWebSecurity
public class ProdSecurityConfig {

    @Bean
    public SecurityFilterChain prodFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(
                                "/login",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll() // 정적 리소스 모두 허용
                        .requestMatchers("/admin/**").authenticated() // admin 하위 경로는 무조건 인증 필요
                        .anyRequest().authenticated() // 그 외(메인, 목록 등) 모두 접근 허용
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/admin/dashboard", true) // 로그인 성공시 이동할 페이지
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/") // 로그아웃 후 메인으로 이동
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
