package com.zosh.job.service.impl;

import com.zosh.job.dto.EducationResponse;
import com.zosh.job.mapper.ResumeMapper;
import com.zosh.job.model.Education;
import com.zosh.job.model.Resume;
import com.zosh.job.payload.AddEducationRequest;
import com.zosh.job.respository.EducationRepository;
import com.zosh.job.service.EducationService;
import com.zosh.job.service.ResumeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationServiceImpl implements EducationService {

  private final EducationRepository educationRepository;
  private final ResumeService resumeService;

  @Override
  public EducationResponse addEducation(Long resumeId, Long candidateId, AddEducationRequest req)
      throws Exception {
    Resume resume = resumeService.getResumeEntity(resumeId);
    assertOwner(resume, candidateId);
    Education education =
        Education.builder()
            .resume(resume)
            .institutionName(req.getInstitutionName())
            .degree(req.getDegree())
            .fieldOfStudy(req.getFieldOfStudy())
            .grade(req.getGrade())
            .startDate(req.getStartDate())
            .endDate(req.getEndDate())
            .isCurrentEducation(Boolean.TRUE.equals(req.getIsCurrentEducation()))
            .description(req.getDescription())
            .displayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0)
            .build();
    Education savedEducation = educationRepository.save(education);

    return ResumeMapper.toEducationResponse(savedEducation);
  }

  @Override
  public List<EducationResponse> getEducations(Long resumeId) throws Exception {
    return educationRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId).stream()
        .map(ResumeMapper::toEducationResponse)
        .toList();
  }

  @Override
  public EducationResponse updateEducation(
      Long educationId, Long resumeId, Long candidateId, AddEducationRequest req) throws Exception {
    Education edu =
        educationRepository
            .findById(educationId)
            .orElseThrow(() -> new Exception("Education not found"));
    assertOwner(edu.getResume(), candidateId);
    assertResume(edu, resumeId);
    edu.setInstitutionName(req.getInstitutionName());
    edu.setDegree(req.getDegree());
    edu.setFieldOfStudy(req.getFieldOfStudy());
    edu.setGrade(req.getGrade());
    edu.setStartDate(req.getStartDate());
    edu.setEndDate(req.getEndDate());
    edu.setIsCurrentEducation(Boolean.TRUE.equals(req.getIsCurrentEducation()));
    edu.setDescription(req.getDescription());
    if (req.getDisplayOrder() != null) {
      edu.setDisplayOrder(req.getDisplayOrder());
    }
    return ResumeMapper.toEducationResponse(educationRepository.save(edu));
  }

  @Override
  public void deleteEducation(Long educationId, Long resumeId, Long candidateId) throws Exception {
    Education edu =
        educationRepository
            .findById(educationId)
            .orElseThrow(() -> new Exception("Education not found"));
    assertOwner(edu.getResume(), candidateId);
    assertResume(edu, resumeId);
    educationRepository.delete(edu);
  }

  private void assertOwner(Resume resume, Long candidateId) throws Exception {
    if (!resume.getCandidateId().equals(candidateId)) {
      throw new Exception("You are not the owner of this resume");
    }
  }

  private void assertResume(Education edu, Long resumeId) throws Exception {
    if (!edu.getResume().getId().equals(resumeId)) {
      throw new Exception("Education does not belong to this resume");
    }
  }
}
