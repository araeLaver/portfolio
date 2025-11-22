package com.portfolio.controller;

import com.portfolio.dto.Project;
import com.portfolio.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProjectService projectService;

    public AdminController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("projects", projectService.getProjects());
        model.addAttribute("newProject", new Project()); // For the add/edit form
        return "admin";
    }

    @PostMapping("/save")
    public String saveProject(@ModelAttribute Project project, @RequestParam("stackString") String stackString, Model model) {
        // ID 유효성 검사
        if (project.getId() == null || project.getId().trim().isEmpty()) {
            model.addAttribute("error", "프로젝트 ID는 필수입니다.");
            model.addAttribute("projects", projectService.getProjects());
            model.addAttribute("newProject", project);
            return "admin";
        }
        
        // 제목 유효성 검사
        if (project.getTitle() == null || project.getTitle().trim().isEmpty()) {
            model.addAttribute("error", "프로젝트 제목은 필수입니다.");
            model.addAttribute("projects", projectService.getProjects());
            model.addAttribute("newProject", project);
            return "admin";
        }
        
        if (stackString != null && !stackString.trim().isEmpty()) {
            project.setStack(java.util.Arrays.asList(stackString.split(",")).stream().map(String::trim).collect(java.util.stream.Collectors.toList()));
        } else {
            project.setStack(java.util.Collections.emptyList());
        }
        
        projectService.saveProject(project);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editProject(@PathVariable String id, Model model) {
        projectService.getProjectById(id).ifPresent(project -> model.addAttribute("newProject", project));
        model.addAttribute("projects", projectService.getProjects()); // To display list on same page
        return "admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return "redirect:/admin";
    }
}
