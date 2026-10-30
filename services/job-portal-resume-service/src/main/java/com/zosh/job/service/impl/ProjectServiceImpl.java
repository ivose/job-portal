package com.zosh.job.service.impl;

import com.zosh.job.dto.ProjectResponse;
import com.zosh.job.mapper.ResumeMapper;
import com.zosh.job.model.Project;
import com.zosh.job.model.Resume;
import com.zosh.job.payload.AddProjectRequest;
import com.zosh.job.respository.ProjectRepository;
import com.zosh.job.service.ProjectService;
import com.zosh.job.service.ResumeService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;
  private final ResumeService resumeService;

  @Override
  public ProjectResponse addProject(Long resumeId, Long candidateId, AddProjectRequest req)
      throws Exception {
    Resume resume = resumeService.getResumeEntity(resumeId);
    assertOwner(resume, candidateId);
    Project project =
        Project.builder()
            .resume(resume)
            .title(req.getTitle())
            .description(req.getDescription())
            .technologies(req.getTechnologies() != null ? req.getTechnologies() : new ArrayList<>())
            .projectUrl(req.getProjectUrl())
            .sourceCodeUrl(req.getSourceCodeUrl())
            .startDate(req.getStartDate())
            .endDate(req.getEndDate())
            .isonGoeing(Boolean.TRUE.equals(req.getIsonGoeing()))
            .displayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0)
            .build();
    Project saved = projectRepository.save(project);
    return ResumeMapper.toProjectResponse(saved);
  }

  @Override
  public List<ProjectResponse> getProjects(Long resumeId) throws Exception {
    return projectRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId).stream()
        .map(ResumeMapper::toProjectResponse)
        .toList();
  }

  @Override
  public ProjectResponse updateProject(
      Long projectId, Long resumeId, Long candidateId, AddProjectRequest req) throws Exception {
    Project project =
        projectRepository.findById(projectId).orElseThrow(() -> new Exception("Project not found"));
    assertOwner(project.getResume(), candidateId);
    assertResume(project, resumeId);
    project.setTitle(req.getTitle());
    project.setDescription(req.getDescription());
    project.setTechnologies(
        req.getTechnologies() != null ? req.getTechnologies() : new ArrayList<>());
    project.setProjectUrl(req.getProjectUrl());
    project.setSourceCodeUrl(req.getSourceCodeUrl());
    project.setStartDate(req.getStartDate());
    project.setEndDate(req.getEndDate());
    if (req.getIsonGoeing() != null) {
      project.setIsonGoeing(req.getIsonGoeing());
    }
    project.setDisplayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0);
    return ResumeMapper.toProjectResponse(projectRepository.save(project));
  }

  @Override
  public void deleteProject(Long projectId, Long resumeId, Long candidateId) throws Exception {
    Project project =
        projectRepository.findById(projectId).orElseThrow(() -> new Exception("Project not found"));
    assertOwner(project.getResume(), candidateId);
    assertResume(project, resumeId);
    projectRepository.delete(project);
  }

  private void assertOwner(Resume resume, Long candidateId) throws Exception {
    if (!resume.getCandidateId().equals(candidateId)) {
      throw new Exception("You are not the owner of this resume");
    }
  }

  private void assertResume(Project project, Long resumeId) throws Exception {
    if (!project.getResume().getId().equals(resumeId)) {
      throw new Exception("Project does not belong to this resume");
    }
  }
}
