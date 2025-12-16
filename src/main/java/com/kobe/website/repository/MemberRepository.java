package com.kobe.website.repository;

import com.kobe.website.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * packageName    : com.kobe.website.repository
 * fileName       : MemberRepository
 * author         : kobe
 * date           : 2025. 12. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 15.        kobe       최초 생성
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
