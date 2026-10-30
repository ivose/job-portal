package com.zosh.job.controller;

import com.zosh.job.dto.ProjectResponse;
import com.zosh.job.payload.AddProjectRequest;
import com.zosh.job.service.ProjectService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resumes/{resumeId}/projects")
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;

  @PostMapping
  public ResponseEntity<ProjectResponse> addProject(
      @PathVariable Long resumeId,
      @RequestHeader("X-User-Id") Long candidateId,
      @Valid @RequestBody AddProjectRequest request)
      throws Exception {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(projectService.addProject(resumeId, candidateId, request));
  }

  @GetMapping
  public ResponseEntity<List<ProjectResponse>> getProjects(@PathVariable Long resumeId)
      throws Exception {

    return ResponseEntity.ok(projectService.getProjects(resumeId));
  }

  @PutMapping("/{projectId}")
  public ResponseEntity<ProjectResponse> updateProject(
      @PathVariable Long resumeId,
      @PathVariable Long projectId,
      @RequestHeader("X-User-Id") Long candidateId,
      @Valid @RequestBody AddProjectRequest request)
      throws Exception {

    return ResponseEntity.ok(
        projectService.updateProject(projectId, resumeId, candidateId, request));
  }

  @DeleteMapping("/{projectId}")
  public ResponseEntity<Void> deleteProject(
      @PathVariable Long resumeId,
      @PathVariable Long projectId,
      @RequestHeader("X-User-Id") Long candidateId)
      throws Exception {

    projectService.deleteProject(projectId, resumeId, candidateId);

    return ResponseEntity.noContent().build();
  }
}
