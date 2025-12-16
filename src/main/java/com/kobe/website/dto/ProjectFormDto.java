package com.kobe.website.dto;

import com.kobe.website.domain.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * packageName    : com.kobe.website.dto
 * fileName       : ProjectFromDto
 * author         : kobe
 * date           : 2025. 12. 16.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 16.        kobe       최초 생성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProjectFormDto {
    private String title;
    private String description;
    private MultipartFile imageFile; // 이미지는 파일로 받습니다.
    private String targetUrl; // 클릭 시 이동할 링크

    // DTO -> Entity 변환 메서드 (빌더 패턴 활용)
    public Project toEntity(String storedFileName) {
        return Project.builder()
                .title(this.title)
                .description(this.description)
                .imageUrl(storedFileName) // 파일명(경로) 저장
                .targetUrl(this.targetUrl)
                .build();
    }
}
