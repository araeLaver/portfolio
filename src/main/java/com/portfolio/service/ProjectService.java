package com.portfolio.service;

import com.portfolio.dto.Project;
import com.portfolio.entity.ProjectEntity;
import com.portfolio.repository.ProjectRepository;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

@Service
public class ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectRepository projectRepository;

    private final Parser parser;
    private final HtmlRenderer renderer;
    private final PolicyFactory htmlSanitizer;

    public ProjectService() {
        this.parser = Parser.builder().build();
        this.renderer = HtmlRenderer.builder().build();

        // OWASP HTML Sanitizer - allow safe markdown HTML elements
        this.htmlSanitizer = new HtmlPolicyBuilder()
            .allowElements("p", "br", "strong", "em", "b", "i", "u", "ul", "ol", "li",
                          "h1", "h2", "h3", "h4", "h5", "h6", "blockquote", "code", "pre",
                          "a", "hr", "table", "thead", "tbody", "tr", "th", "td")
            .allowAttributes("href").onElements("a")
            .allowAttributes("class").globally()
            .requireRelNofollowOnLinks()
            .toFactory();
    }

    @Transactional(readOnly = true)
    public List<Project> getProjects() {
        // Stack is EAGER loaded, no need to force initialize
        return projectRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Project> getProjectById(String id) {
        return projectRepository.findById(id)
                .map(entity -> {
                    Project project = convertToDto(entity);
                    if (project.getDetails() != null && !project.getDetails().isEmpty()) {
                        // Parse markdown to HTML
                        Node document = parser.parse(project.getDetails());
                        String rawHtml = renderer.render(document);
                        // Sanitize HTML to prevent XSS
                        String sanitizedHtml = htmlSanitizer.sanitize(rawHtml);
                        project.setDetails(sanitizedHtml);
                    }
                    return project;
                });
    }

    @Transactional
    public void saveProject(Project project) {
        ProjectEntity entity = convertToEntity(project);
        projectRepository.save(entity);
        logger.info("Saved project to database: {}", project.getTitle());
    }

    @Transactional
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
        logger.info("Deleted project with ID: {}", id);
    }

    private Project convertToDto(ProjectEntity entity) {
        Project dto = new Project();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setGitUrl(entity.getGitUrl());
        dto.setLiveUrl(entity.getLiveUrl());
        dto.setStack(entity.getStack());
        dto.setDetails(entity.getDetails());
        return dto;
    }

    private ProjectEntity convertToEntity(Project dto) {
        ProjectEntity entity = new ProjectEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setGitUrl(dto.getGitUrl());
        entity.setLiveUrl(dto.getLiveUrl());
        entity.setStack(dto.getStack());
        entity.setDetails(dto.getDetails());
        return entity;
    }
}
