package com.kobe.website.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * packageName    : com.kobe.website.util
 * fileName       : ImageResizeUtil
 * author         : kobe
 * date           : 2025. 12. 19.
 * description    : 이미지 리사이징 유틸리티 클래스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 19.        kobe       최초 생성
 */
@Component
public class ImageResizeUtil {

    // 썸네일 이미지 최대 크기 (16:9 비율 유지)
    private static final int MAX_WIDTH = 1200;
    private static final int MAX_HEIGHT = 675; // 1200 * 9/16 = 675

    /**
     * 이미지를 표준 크기로 리사이징
     * @param file 원본 이미지 파일
     * @return 리사이징된 이미지의 InputStream
     * @throws IOException 이미지 처리 중 오류 발생 시
     */
    public ByteArrayInputStream resizeImage(MultipartFile file) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // 이미지 리사이징 (비율 유지, 최대 크기 제한)
        Thumbnails.of(file.getInputStream())
                .size(MAX_WIDTH, MAX_HEIGHT) // 최대 크기 설정
                .outputFormat("jpg") // JPEG 형식으로 통일 (용량 최적화)
                .outputQuality(0.85) // 품질 85% (용량과 품질의 균형)
                .toOutputStream(outputStream);

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    /**
     * 이미지가 리사이징이 필요한지 확인
     * @param file 원본 이미지 파일
     * @return 리사이징 필요 여부 (항상 true로 반환하여 일관된 크기 유지)
     */
    public boolean needsResize(MultipartFile file) {
        // 모든 이미지를 표준 크기로 리사이징하여 일관성 유지
        return true;
    }
}

