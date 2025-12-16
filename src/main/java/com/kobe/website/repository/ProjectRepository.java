package com.kobe.website.repository;

import com.kobe.website.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.kobe.website.repository
 * fileName       : ProjectRepository
 * author         : kobe
 * date           : 2025. 12. 16.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 16.        kobe       최초 생성
 */

// JpaRepository<Entity 타입, PK 타입>을 상속받으면
// save(), findById(), findAll(), delete() 등의 메서드가 자동으로 생성됩니다.
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // 추후 "최신순 정렬" 같은 기능이 필요할 때 아래와 같은 메서드 이름만으로 쿼리를 만들 수 있습니다.
    // List<Project> findAllByOrderByCreatedDateDesc();
}
