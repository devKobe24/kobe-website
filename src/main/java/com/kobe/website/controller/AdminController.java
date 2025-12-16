package com.kobe.website.controller;

import com.kobe.website.domain.Project;
import com.kobe.website.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * packageName    : com.kobe.website.controller
 * fileName       : AdminController
 * author         : kobe
 * date           : 2025. 12. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 15.        kobe       최초 생성
 */
@Controller
public class AdminController {

    private final ProjectService projectService;

    public AdminController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // 1. 로그인 페이지 연결
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // templates/login.html을 찾음
    }

    // 2. 관리자 대시보드 연결 (로그인 성공 시 이동)
    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        return "admin/dashboard"; // templates/admin/dashboard.html을 찾음
    }

    @GetMapping("/admin/projects")
    public String projects(Model model) {
        // DB에서 목록 가져오기
        List<Project> projects = projectService.findAllProjectsSortByDesc();

        // 화면(view)에 "projects"라는 이름으로 전달
        model.addAttribute("projects", projects);
        return "admin/project-list";
    }
}
