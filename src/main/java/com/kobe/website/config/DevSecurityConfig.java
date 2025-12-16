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
 * fileName       : DevSecurityConfig
 * author         : kobe
 * date           : 2025. 12. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 15.        kobe       최초 생성
 */
@Profile("dev")
@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터 체인 활성화
public class DevSecurityConfig {

    @Bean
    public SecurityFilterChain devFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. 요청 URL별 권한 설정
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(
                                "/login",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/h2-console/**"
                                ).permitAll() // 정적 리소스 모두 허용
                        .requestMatchers("/admin/**").authenticated() // admin 하위 경로는 무조건 인증 필요
                        .anyRequest().permitAll() // 그 외(메인, 목록 등) 모두 접근 허용
                )
                // 2. 로그인 폼 설정.
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/admin/dashboard", true) // 로그인 성공시 이동할 페이지
                        .permitAll()
                )
                // 3. 로그아웃 설정
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/") // 로그아웃 후 메인으로 이동
                        .permitAll()
                )
                // 4. CSRF 예외 처리 (H2 Console은 POST 사용)
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                // 5. iframe 허용 (H2 Console 필수)
                .headers((headers) -> headers
                        .frameOptions(frame -> frame.disable())
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
