package com.kobe.website.controller;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.kobe.website.controller
 * fileName       : ImageController
 * author         : kobe
 * date           : 2025. 12. 19.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 19.        kobe       최초 생성
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    // 중요: {filename:.+} 는 확장자(.png 등)까지 모두 파일명으로 인식하게 함
    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        log.info("이미지 요청 들어옴: {}", filename); // [디버깅용 로그]

        try {
            // S3에서 파일 다운로드 (경로: uploads/파일명)
            S3Resource resource = s3Template.download(bucketName, "uploads/" + filename);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // 혹은 동적으로 감지
                    .body(resource);
        } catch (Exception e) {
            log.error("이미지 다운로드 실패 (파일이 없거나 S3 접근 불가): {}", filename, e);
            return ResponseEntity.notFound().build();
        }
    }
}