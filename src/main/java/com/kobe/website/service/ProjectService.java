package com.kobe.website.service;

import com.kobe.website.domain.Project;
import com.kobe.website.dto.ProjectFormDto;
import com.kobe.website.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional // 쓰기 작업이므로 readOnly = false(기본값)
    public Long saveProject(ProjectFormDto formDto) throws IOException {
        String storedFileName = null;

        // 1. 파일이 첨부되었는지 확인
        if (formDto.getImageFile() != null && !formDto.getImageFile().isEmpty()) {
            storedFileName = saveFile(formDto.getImageFile());
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

        // 2. 연결된 이미지 파일이 있다면 삭제
        if (project.getImageUrl() != null) {
            // DB에는 "/uploads/uuid_파일명" 형태로 저장되어 있음
            // 실제 파일 경로: uploadDir + "uuid_파일명"
            String storedFileName = project.getImageUrl().replace("/upload", "");
            File file = new File(uploadDir + "/" + storedFileName);

            if (file.exists()) {
                if (file.delete()) {
                    log.info("파일 삭제 성공: {}", storedFileName);
                } else {
                    log.warn("파일 삭제 실패: {}", storedFileName);
                }
            }
        }

        // 3. DB 데이터 삭제
        projectRepository.delete(project);
    }

    // [파일 저장 내부 메서드]
    private String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        // 폴더가 없으면 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 파일명 중복 방지를 위한 UUID 생성 (예: uuid-original.jpg)
        String originalFilename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + "_" + originalFilename;

        // 실제 파일 저장 (서버 디스크에)
        file.transferTo(new File(uploadDir + "/" + saveFileName));

        // DB에 저장할 경로 리턴 (웹 접근용 경로)
        return "/uploads/" + saveFileName;
    }
}
