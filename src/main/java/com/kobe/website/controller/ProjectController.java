package com.kobe.website.controller;

import com.kobe.website.dto.ProjectFormDto;
import com.kobe.website.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * packageName    : com.kobe.website.controller
 * fileName       : ProjectController
 * author         : kobe
 * date           : 2025. 12. 16.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 16.        kobe       최초 생성
 */
@Slf4j
@Controller
@RequestMapping("/admin/projects") // 공통 URL 프리픽스
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // 1. 글쓰기 폼 화면 보여주기
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("projectForm", new ProjectFormDto());
        return "admin/project-form"; // templates/admin/project-form.html 로 이동
    }

    // 2. 글쓰기 요청 처리 (DB 저장)
    @PostMapping("/new")
    public String create(@ModelAttribute ProjectFormDto form) throws IOException {
        log.info("새 프로젝트 등록 요청: {}", form);

        projectService.saveProject(form);

        // 저장이 끝나면 대시보드로 리다이렉트 (PRG 패턴)
        return "redirect:/admin/dashboard";
    }

    // 3. 삭제 요청 처리
    // URL: /admin/project/{id}/delete (POST)
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/admin/dashboard"; // 삭제 후 목록으로 복귀
    }
}
