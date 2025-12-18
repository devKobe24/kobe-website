package com.kobe.website.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
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
@EnableWebSecurity
public class DevSecurityConfig {

    @Bean
    public SecurityFilterChain devFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        // 1. 정적 리소스 (css, js, images 등) 자동 허용
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                        // 2. H2 Console 자동 허용 (PathRequest 사용)
                        .requestMatchers(PathRequest.toH2Console()).permitAll()

                        // 3. 이미지 업로드 경로 허용 (Prod와 맞춤)
                        .requestMatchers("/uploads/**").permitAll()

                        // 4. 로그인 페이지 허용
                        .requestMatchers("/", "/login").permitAll()

                        // 5. [권장] 개발 환경도 운영처럼 잠그는 것이 좋습니다. (실수 방지)
                        // 만약 너무 불편하면 .anyRequest().permitAll() 로 되돌리셔도 됩니다.
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/admin/dashboard", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                // 6. H2 Console을 위한 CSRF 예외 처리 (PathRequest 사용)
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers(PathRequest.toH2Console())
                )
                // 7. H2 Console을 위한 X-Frame-Options 해제
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
