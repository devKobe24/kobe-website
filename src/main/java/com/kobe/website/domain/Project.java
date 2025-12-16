package com.kobe.website.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * packageName    : com.kobe.website.domain
 * fileName       : Project
 * author         : kobe
 * date           : 2025. 12. 16.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 16.        kobe       최초 생성
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class) // 생성일/수정일 자동 관리를 위해 필수
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // 프로젝트 제목

    @Column(columnDefinition = "TEXT") // 긴 내용 저장
    private String description; // 프로젝트 설명

    private String imageUrl; // 썸네일 이미지 파일명 / 경로

    private String targetUrl;

    @CreatedDate
    @Column(updatable = false) // 생성일은 수정 시 변경되지 않도록 설정
    private LocalDateTime createdDate; // 생성일 (HTML에서 찾는 필드명)

    @LastModifiedDate
    private LocalDateTime lastModifiedDate; // 마지막 수정일

    @Builder
    public Project(String title, String description, String imageUrl, String targetUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.targetUrl = targetUrl;
    }

    // 수정 기능을 위한 메서드
    public void update(String title, String description, String imageUrl, String targetUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.targetUrl = targetUrl;
    }
}
