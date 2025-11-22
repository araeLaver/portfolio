package com.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.util.List;

public class Project {
    @NotBlank(message = "프로젝트 ID는 필수입니다")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "ID는 영문 소문자, 숫자, 하이픈만 사용 가능합니다")
    @Size(max = 100, message = "ID는 100자 이하여야 합니다")
    private String id;

    @NotBlank(message = "제목은 필수입니다")
    @Size(min = 1, max = 200, message = "제목은 1-200자 이내여야 합니다")
    private String title;

    @Size(max = 500, message = "설명은 500자 이하여야 합니다")
    private String description;

    @URL(message = "유효한 URL 형식이어야 합니다")
    @Size(max = 500, message = "Git URL은 500자 이하여야 합니다")
    private String gitUrl;

    @URL(message = "유효한 URL 형식이어야 합니다")
    @Size(max = 500, message = "Live URL은 500자 이하여야 합니다")
    private String liveUrl;

    @Size(max = 20, message = "기술 스택은 최대 20개까지 가능합니다")
    private List<String> stack;

    @Size(max = 50000, message = "상세 설명은 50000자 이하여야 합니다")
    private String details;

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
