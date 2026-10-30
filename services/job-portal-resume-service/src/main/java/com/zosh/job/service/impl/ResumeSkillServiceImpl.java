package com.zosh.job.service.impl;

import com.zosh.job.dto.ResumeSkillResponse;
import com.zosh.job.mapper.ResumeMapper;
import com.zosh.job.model.Resume;
import com.zosh.job.model.ResumeSkill;
import com.zosh.job.payload.AddResumeSkillRequest;
import com.zosh.job.respository.ResumeSkillRepository;
import com.zosh.job.service.ResumeService;
import com.zosh.job.service.ResumeSkillService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeSkillServiceImpl implements ResumeSkillService {
  private final ResumeSkillRepository resumeSkillRepository;
  private final ResumeService resumeService;

  @Override
  public ResumeSkillResponse addSkill(Long resumeId, long candidateId, AddResumeSkillRequest req)
      throws Exception {
    Resume resume = resumeService.getResumeEntity(resumeId);
    assertOwner(resume, candidateId);
    ResumeSkill skill =
        ResumeSkill.builder()
            .resume(resume)
            .skillName(req.getSkillName())
            .proficiencyLevel(req.getProficiencyLevel())
            .yearsOfExperience(req.getYearsOfExperience())
            .displayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0)
            .build();
    ResumeSkill saved = resumeSkillRepository.save(skill);
    return ResumeMapper.toSkillResponse(saved);
  }

  @Override
  public List<ResumeSkillResponse> getSkills(Long resumeId) throws Exception {
    return resumeSkillRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId).stream()
        .map(ResumeMapper::toSkillResponse)
        .toList();
  }

  @Override
  public ResumeSkillResponse updateSkill(
      Long resumeId, Long skillId, long candidateId, AddResumeSkillRequest request)
      throws Exception {
    ResumeSkill skill =
        resumeSkillRepository.findById(skillId).orElseThrow(() -> new Exception("Skill not found"));
    assertOwner(skill.getResume(), candidateId);
    skill.setSkillName(request.getSkillName());
    skill.setProficiencyLevel(request.getProficiencyLevel());
    skill.setYearsOfExperience(request.getYearsOfExperience());
    if (request.getDisplayOrder() != null) {
      skill.setDisplayOrder(request.getDisplayOrder());
    }
    return ResumeMapper.toSkillResponse(resumeSkillRepository.save(skill));
  }

  @Override
  public void deleteSkill(Long skillId, Long resumeId, long candidateId) throws Exception {
    ResumeSkill skill =
        resumeSkillRepository.findById(skillId).orElseThrow(() -> new Exception("Skill not found"));
    assertOwner(skill.getResume(), candidateId);
    assertResume(skill, resumeId);
    resumeSkillRepository.delete(skill);
  }

  private void assertOwner(Resume resume, Long candidateId) throws Exception {
    if (!resume.getCandidateId().equals(candidateId)) {
      throw new Exception("resume not found");
    }
  }

  private void assertResume(ResumeSkill skill, Long resumeId) throws Exception {
    if (!skill.getResume().getId().equals(resumeId)) {
      throw new Exception("Skill does not belong to this resume");
    }
  }
}
