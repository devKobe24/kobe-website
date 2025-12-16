package com.kobe.website.service;

import com.kobe.website.domain.Member;
import com.kobe.website.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.kobe.website.service
 * fileName       : CustomUserDetailsService
 * author         : kobe
 * date           : 2025. 12. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 15.        kobe       최초 생성
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // DB의 Member 객체를 Security가 이해할 수 있는 UserDetails 객체로 변환
        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword()) // DB엔 이미 암호화된 값이 있어야 함
                .roles(member.getRole()) // "ADMIN -> ROLE_ADMIN 자동 변환"
                .build();

    }
}
