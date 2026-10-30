package com.zosh.job.service.impl;

import com.zosh.job.dto.WorkExperienceResponse;
import com.zosh.job.mapper.WorkExperienceMapper;
import com.zosh.job.model.Resume;
import com.zosh.job.model.WorkExperience;
import com.zosh.job.payload.AddWorkExperience;
import com.zosh.job.respository.WorkExperienceRepository;
import com.zosh.job.service.ResumeService;
import com.zosh.job.service.WorkExperienceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkExperienceServiceImpl implements WorkExperienceService {
  private final WorkExperienceRepository workExperienceRepository;
  private final ResumeService resumeService;

  @Override
  public WorkExperienceResponse addWorkExperience(
      Long resumeId, Long candidateId, AddWorkExperience req) throws Exception {
    Resume resume = resumeService.getResumeEntity(resumeId);
    assertOwner(resume, candidateId);
    WorkExperience workExperience =
        WorkExperience.builder()
            .resume(resume)
            .companyName(req.getCompanyName())
            .companyLogoUrl(req.getCompanyLogoUrl())
            .jobTitle(req.getJobTitle())
            .employmentType(req.getEmploymentType())
            .location(req.getLocation())
            .startDate(req.getStartDate())
            .endDate(req.getEndDate())
            .isCurrentJob(Boolean.TRUE.equals(req.getIsCurrentJob()))
            .description(req.getDescription())
            .technologies(req.getTechnologies() != null ? req.getTechnologies() : List.of())
            .displayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0)
            .build();
    WorkExperience saved = workExperienceRepository.save(workExperience);
    return WorkExperienceMapper.toWorkExperienceResponse(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public List<WorkExperienceResponse> getWorkExperiences(Long resumeId) throws Exception {

    resumeService.getResumeEntity(resumeId);

    return workExperienceRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId).stream()
        .map(WorkExperienceMapper::toWorkExperienceResponse)
        .toList();
  }

  @Override
  public WorkExperienceResponse updateWorkExperience(
      Long resumeId, Long workExperienceId, Long candidateId, AddWorkExperience req)
      throws Exception {

    WorkExperience workExperience = getWorkExperienceEntity(workExperienceId);

    assertResumeAndOwner(workExperience, resumeId, candidateId);

    workExperience.setCompanyName(req.getCompanyName());
    workExperience.setCompanyLogoUrl(req.getCompanyLogoUrl());
    workExperience.setJobTitle(req.getJobTitle());
    workExperience.setEmploymentType(req.getEmploymentType());
    workExperience.setLocation(req.getLocation());
    workExperience.setStartDate(req.getStartDate());
    workExperience.setEndDate(req.getEndDate());
    workExperience.setIsCurrentJob(Boolean.TRUE.equals(req.getIsCurrentJob()));
    workExperience.setDescription(req.getDescription());
    workExperience.setTechnologies(
        req.getTechnologies() != null ? req.getTechnologies() : List.of());
    workExperience.setDisplayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0);

    WorkExperience updated = workExperienceRepository.save(workExperience);

    return WorkExperienceMapper.toWorkExperienceResponse(updated);
  }

  @Override
  public void deleteWorkExperience(Long resumeId, Long workExperienceId, Long candidateId)
      throws Exception {}

  @Override
  public WorkExperience getWorkExperienceEntity(Long workExperienceId) throws Exception {
    return workExperienceRepository
        .findById(workExperienceId)
        .orElseThrow(() -> new Exception("Work experience not found"));
  }

  private void assertOwner(Resume resume, Long candidateId) throws Exception {
    if (!resume.getCandidateId().equals(candidateId)) {
      throw new Exception("resume not found");
    }
  }

  private void assertResumeAndOwner(WorkExperience workExperience, Long resumeId, Long candidateId)
      throws Exception {

    Resume resume = workExperience.getResume();

    if (!resume.getId().equals(resumeId)) {
      throw new Exception("Work experience does not belong to this resume");
    }

    assertOwner(resume, candidateId);
  }
}
