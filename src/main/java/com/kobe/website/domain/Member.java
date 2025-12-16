package com.kobe.website.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.kobe.website.domain
 * fileName       : Member
 * author         : kobe
 * date           : 2025. 12. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 15.        kobe       최초 생성
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member { // 'User'는 DB 예약어일 확률이 높아 Member 등을 선호함

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // 로그인 ID

    @Column(nullable = false)
    private String password; // 암호화된 비밀번호

    @Column(nullable = false)
    private String role; // ROLE_ADMIN, ROLE_USER

    @Builder
    public Member(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
