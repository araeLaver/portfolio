package com.portfolio.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects", schema = "java_portfolio")
public class ProjectEntity {
    
    @Id
    private String id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "git_url")
    private String gitUrl;
    
    @Column(name = "live_url")
    private String liveUrl;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_stack", schema = "java_portfolio", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "technology")
    private List<String> stack;
    
    @Column(columnDefinition = "TEXT")
    private String details;
    
    // Default constructor
    public ProjectEntity() {}
    
    // Constructor
    public ProjectEntity(String id, String title, String description, String gitUrl, String liveUrl, List<String> stack, String details) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.gitUrl = gitUrl;
        this.liveUrl = liveUrl;
        this.stack = stack;
        this.details = details;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getLiveUrl() {
        return liveUrl;
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }

    public List<String> getStack() {
        return stack;
    }

    public void setStack(List<String> stack) {
        this.stack = stack;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}