package com.kobe.website.service;

import com.kobe.website.domain.Project;
import com.kobe.website.dto.ProjectFormDto;
import com.kobe.website.repository.ProjectRepository;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * packageName    : com.kobe.website.service
 * fileName       : ProjectService
 * author         : kobe
 * date           : 2025. 12. 16.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 16.        kobe       최초 생성
 */
@Slf4j
@Service
@Transactional(readOnly = true) // 기본적으로 읽기 전용 (성능 최적화)
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final S3Template s3Template; // S3와 통신하는 핵심 도구

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    @Transactional // 쓰기 작업이므로 readOnly = false(기본값)
    public Long saveProject(ProjectFormDto formDto) throws IOException {
        String storedFileName = null;

        // 1. 파일이 첨부되었는지 확인
        if (formDto.getImageFile() != null && !formDto.getImageFile().isEmpty()) {
            storedFileName = saveFileToS3(formDto.getImageFile());
        }


        // 2. DTO -> Entity 변환 (파일명 포함)
        Project project = formDto.toEntity(storedFileName);

        projectRepository.save(project);
        return project.getId();
    }

    // 전체 프로젝트 목록 조회 (최신순)
    public List<Project> findAllProjectsSortByDesc() {
        return projectRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    // 프로젝트 삭제 기능
    @Transactional
    public void deleteProject(Long id) {
        // 1. 대상 찾기
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 없습니다. id=" + id));

        // 2. 이미지가 있다면 S3에서 삭제
        if (project.getImageUrl() != null) {
            // DB 저장값: "/uploads/uuid_파일명.jpg" -> S3 키 : "uploads/uuid_파일명.jpg"
            // 맨 앞의 슬래시(/)를 제거해야 S3가 경로를 인식합니다.
            String s3Key = project.getImageUrl().substring(1);

            try {
                s3Template.deleteObject(bucketName, s3Key);
                log.info("S3 파일 삭제 성공: {}", s3Key);
            } catch (Exception e) {
                log.error("S3 파일 삭제 실패: {}", s3Key, e);
                // 파일 삭제 실패해도 DB 데이터는 삭제되도록 예외를 던지지 않음(선택 사항)
            }
        }

        // 3. DB 데이터 삭제
        projectRepository.delete(project);
    }

    // [내부 메서드] S3 업로드 로직
    private String saveFileToS3(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        // S3에 저장될 파일 이름 (폴더 경로 포함)
        // 예: uploads/uuid-original.jpg
        String s3Key = "uploads/" + uuid + "_" + originalFilename;

        // S3로 업로드
        s3Template.upload(bucketName, s3Key, file.getInputStream());

        log.info("S3 업로드 완료: {}", s3Key);

        // DB에는 웹에서 접근할 경로를 저장 (나중에 이미지 불러오기용 API 경로)
        return "/" + s3Key;
    }
}
