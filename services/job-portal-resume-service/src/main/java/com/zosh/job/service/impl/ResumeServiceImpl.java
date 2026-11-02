package com.zosh.job.service.impl;

import com.zosh.job.domain.ResumeTemplate;
import com.zosh.job.domain.ResumeVisibility;
import com.zosh.job.dto.EducationResponse;
import com.zosh.job.dto.LanguageResponse;
import com.zosh.job.dto.PersonalInfoResponse;
import com.zosh.job.dto.ProjectResponse;
import com.zosh.job.dto.ResumeResponse;
import com.zosh.job.dto.ResumeSkillResponse;
import com.zosh.job.dto.WorkExperienceResponse;
import com.zosh.job.mapper.ResumeMapper;
import com.zosh.job.mapper.WorkExperienceMapper;
import com.zosh.job.model.PersonalInfo;
import com.zosh.job.model.Resume;
import com.zosh.job.payload.CreateResumeRequest;
import com.zosh.job.respository.EducationRepository;
import com.zosh.job.respository.LanguageRepository;
import com.zosh.job.respository.ProjectRepository;
import com.zosh.job.respository.ResumeRepository;
import com.zosh.job.respository.ResumeSkillRepository;
import com.zosh.job.respository.WorkExperienceRepository;
import com.zosh.job.service.ResumeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeServiceImpl implements ResumeService {

  private final ResumeRepository resumeRepository;
  private final WorkExperienceRepository workExperienceRepository;
  private final EducationRepository educationRepository;
  private final ResumeSkillRepository resumeSkillRepository;
  private final ProjectRepository projectRepository;
  private final LanguageRepository languageRepository;

  @Override
  public ResumeResponse createResume(Long candidateId, CreateResumeRequest req) throws Exception {

    if (Boolean.TRUE.equals(req.getIsDefault())) {
      clearDefaultResume(candidateId);
    }

    Resume resume =
        Resume.builder()
            .candidateId(candidateId)
            .title(req.getTitle())
            .template(req.getTemplate() == null ? ResumeTemplate.PROFESSIONAL : req.getTemplate())
            .visibility(req.getVisibility() == null ? ResumeVisibility.PUBLIC : req.getVisibility())
            .isDefault(Boolean.TRUE.equals(req.getIsDefault()))
            .isActive(true)
            .build();

    return buildFullResponse(resumeRepository.save(resume));
  }

  @Override
  @Transactional(readOnly = true)
  public ResumeResponse getResumeById(Long resumeId, Long candidateId) throws Exception {
    Resume resume = getResumeEntity(resumeId);
    assertOwner(resume, candidateId);
    return buildFullResponse(resume);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ResumeResponse> getMyResumes(Long candidateId) throws Exception {

    return resumeRepository.findByCandidateIdAndIsActiveTrue(candidateId).stream()
        .map(this::buildFullResponse)
        .toList();
  }

  @Override
  public ResumeResponse updatePersonalInfo(
      Long resumeId, Long candidateId, PersonalInfoResponse req) throws Exception {
    Resume resume = getResumeEntity(resumeId);
    assertOwner(resume, candidateId);
    PersonalInfo info = resume.getPersonalInfo();
    if (info == null) {
      info = new PersonalInfo();
    }
    if (req.getFirstName() != null) {
      info.setFirstName(req.getFirstName());
    }
    if (req.getLastName() != null) {
      info.setLastName(req.getLastName());
    }
    if (req.getHeadline() != null) info.setHeadline(req.getHeadline());
    if (req.getEmail() != null) info.setEmail(req.getEmail());
    if (req.getPhone() != null) info.setPhone(req.getPhone());
    if (req.getCity() != null) info.setCity(req.getCity());
    if (req.getCountry() != null) info.setCountry(req.getCountry());
    if (req.getLinkedinUrl() != null) info.setLinkedinUrl(req.getLinkedinUrl());
    if (req.getGithubUrl() != null) info.setGithubUrl(req.getGithubUrl());
    if (req.getPortfolioUrl() != null) info.setPortfolioUrl(req.getPortfolioUrl());
    if (req.getWebsiteUrl() != null) info.setWebsiteUrl(req.getWebsiteUrl());
    resume.setPersonalInfo(info);
    Resume updated = resumeRepository.save(resume);
    return buildFullResponse(updated);
  }

  @Override
  public ResumeResponse updateResume(Long resumeId, Long candidateId, CreateResumeRequest req)
      throws Exception {

    Resume resume = getResumeEntity(resumeId);
    assertOwner(resume, candidateId);

    resume.setTitle(req.getTitle());

    resume.setTemplate(req.getTemplate() == null ? ResumeTemplate.PROFESSIONAL : req.getTemplate());

    resume.setVisibility(
        req.getVisibility() == null ? ResumeVisibility.PUBLIC : req.getVisibility());

    if (Boolean.TRUE.equals(req.getIsDefault())) {
      clearDefaultResume(candidateId);
    }

    resume.setIsDefault(Boolean.TRUE.equals(req.getIsDefault()));

    return buildFullResponse(resume);
  }

  @Override
  public ResumeResponse updateSummary(Long resumeId, Long candidateId, String summary)
      throws Exception {
    Resume resume = getResumeEntity(resumeId);
    assertOwner(resume, candidateId);
    resume.setSummary(summary);
    Resume updated = resumeRepository.save(resume);
    return buildFullResponse(updated);
  }

  @Override
  public ResumeResponse setDefaultResume(Long resumeId, Long candidateId) throws Exception {
    Resume resume = getResumeEntity(resumeId);
    assertOwner(resume, candidateId);
    clearDefaultResume(candidateId);

    resume.setIsDefault(true);
    Resume updated = resumeRepository.save(resume);
    return buildFullResponse(updated);
  }

  @Override
  public void deleteResume(Long resumeId, Long candidateId) throws Exception {
    Resume resume = getResumeEntity(resumeId);
    assertOwner(resume, candidateId);
    resume.setIsActive(false);
    resume.setIsDefault(false);
    resumeRepository.save(resume);
  }

  @Override
  @Transactional(readOnly = true)
  public Resume getResumeEntity(Long resumeId) throws Exception {
    return resumeRepository.findById(resumeId).orElseThrow(() -> new Exception("Resume not found"));
  }

  private void clearDefaultResume(Long candidateId) {

    resumeRepository
        .findByCandidateIdAndIsDefaultTrue(candidateId)
        .ifPresent(
            existing -> {
              existing.setIsDefault(false);
              resumeRepository.save(existing);
            });
  }

  private void assertOwner(Resume resume, Long candidateId) throws Exception {
    if (!resume.getCandidateId().equals(candidateId)) {
      throw new Exception("You are not the owner of this resume");
    }
  }

  private ResumeResponse buildFullResponse(Resume resume) {
    Long resumeId = resume.getId();
    List<WorkExperienceResponse> workExperiences =
        workExperienceRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId).stream()
            .map(WorkExperienceMapper::toWorkExperienceResponse)
            .toList();
    List<EducationResponse> educations =
        educationRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId).stream()
            .map(ResumeMapper::toEducationResponse)
            .toList();
    List<ResumeSkillResponse> resumeSkills =
        resumeSkillRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId).stream()
            .map(ResumeMapper::toSkillResponse)
            .toList();
    List<ProjectResponse> projects =
        projectRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId).stream()
            .map(ResumeMapper::toProjectResponse)
            .toList();
    List<LanguageResponse> languages =
        languageRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId).stream()
            .map(ResumeMapper::toLanguageResponse)
            .toList();
    return ResumeMapper.toResumeResponse(
        resume, workExperiences, educations, resumeSkills, projects, languages);
  }
}
