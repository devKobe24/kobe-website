package com.kobe.website.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
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
        http.authorizeHttpRequests((auth) -> auth
                        // 1. 정적 리소스(css, js, images, favicon 등)는 스프링 부트 기본 설정으로 한방에 허용
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                        // 2. 이미지 업로드 경로 허용 (ImageController)
                        .requestMatchers("/uploads/**").permitAll()

                        // 3. 메인 페이지 및 로그인 페이지 허용
                        .requestMatchers("/", "/login").permitAll()

                        // 4. [중요] 프로젝트 관련 URL은 '조회(GET)'만 누구나 가능 (쓰기/삭제는 막음)
                        .requestMatchers(HttpMethod.GET, "/projects/**").permitAll()

                        // 5. 관리자 페이지는 인증된 사용자만 접근 가능
                        .requestMatchers("/admin/**").authenticated()

                        // 6. 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/admin/dashboard", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        // 참고: Prod 환경에서는 csrf를 끄지 않는 것이 정석입니다. (기본값: enable)
        // HTML Form에서 <input type="hidden" ... csrf> 토큰을 잘 보내야 합니다 (Thymeleaf는 자동 처리됨)

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
