package com.portfolio.controller;

import com.portfolio.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("projects", projectService.getProjects());
        return "index";
    }

    @GetMapping("/projects/{id}")
    public String projectDetails(@PathVariable String id, Model model) {
        try {
            return projectService.getProjectById(id)
                    .map(project -> {
                        model.addAttribute("project", project);
                        return "project-details";
                    })
                    .orElse("redirect:/");
        } catch (Exception e) {
            logger.error("Error loading project with ID: {} - {}", id, e.getMessage(), e);
            return "redirect:/";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
