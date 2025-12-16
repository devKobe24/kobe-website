package com.kobe.website.controller;

import com.kobe.website.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName    : com.kobe.website.controller
 * fileName       : MainController
 * author         : kobe
 * date           : 2025. 12. 16.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 16.        kobe       최초 생성
 */
@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProjectService projectService;

    @GetMapping("/") // 최상위 경로 (메인 페이지)
    public String index(Model model) {
        // 최신순으로 정렬된 프로젝트 목록을 가져와 "projects"라는 이름으로 전달
        model.addAttribute("projects", projectService.findAllProjectsSortByDesc());
        return "index"; // templates/index.html을 찾아감
    }
}
