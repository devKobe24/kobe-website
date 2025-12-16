package com.kobe.website.config;

import com.kobe.website.domain.Member;
import com.kobe.website.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.kobe.website.config
 * fileName       : InitData
 * author         : kobe
 * date           : 2025. 12. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 15.        kobe       최초 생성
 */
@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(InitData.class);

    // application-dev.yml의 값을 주입받음
    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        // DB에 'admin' 계정이 있는지 확인하고 없으면 생성
        if (memberRepository.findByUsername(adminUsername).isEmpty()) {
            Member admin = Member.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword)) // 비밀번호 암호화 필수
                    .role("ADMIN")
                    .build();

            memberRepository.save(admin);
            log.info(">> [INIT] 관리자 계정 생성 완료 ID: {}", admin.getUsername());
        }
    }
}
