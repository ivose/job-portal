package com.zosh.job.service.impl;

import com.zosh.job.dto.JobSkillResponse;
import com.zosh.job.mapper.JobSkillMapper;
import com.zosh.job.model.JobSkill;
import com.zosh.job.payload.JobSkillRequest;
import com.zosh.job.repository.JobSkillsRepository;
import com.zosh.job.service.JobSkillService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JobSkillServiceImpl implements JobSkillService {

  private final JobSkillsRepository jobSkillsRepository;

  @Override
  public JobSkillResponse createJobSkill(JobSkillRequest req) throws Exception {
    if (req.getName().isEmpty()) {
      throw new Exception("Name cannot be empty");
    }
    if (jobSkillsRepository.existsByName(req.getName())) {
      throw new Exception("Skill already exists");
    }
    String slug = generateUniqueSlug(req.getName());
    JobSkill jobSkill =
        JobSkill.builder().name(req.getName()).slug(slug).category(req.getCategory()).build();
    JobSkill savedJobSkill = jobSkillsRepository.save(jobSkill);
    return JobSkillMapper.toJobSkillResponse(savedJobSkill);
  }

  @Override
  public List<JobSkillResponse> getAllJobSkills() throws Exception {
    return jobSkillsRepository.findByActiveTrue().stream()
        .map(JobSkillMapper::toJobSkillResponse)
        .collect(Collectors.toList());
  }

  @Override
  public JobSkillResponse getSkillById(Long id) throws Exception {
    JobSkill skill =
        jobSkillsRepository.findById(id).orElseThrow(() -> new Exception("Skill not found"));
    return JobSkillMapper.toJobSkillResponse(skill);
  }

  @Override
  public JobSkillResponse updateSkill(Long id, JobSkillRequest req) throws Exception {
    JobSkill skill =
        jobSkillsRepository.findById(id).orElseThrow(() -> new Exception("Skill not found"));
    if (!skill.getName().equals(req.getName())
        && jobSkillsRepository.existsByName(skill.getName())) {
      throw new Exception("Skill already exists");
    }
    skill.setName(req.getName());
    skill.setCategory(req.getCategory());
    JobSkill updated = jobSkillsRepository.save(skill);
    return JobSkillMapper.toJobSkillResponse(updated);
  }

  @Override
  public void deleteSkill(Long id) throws Exception {
    JobSkill skill =
        jobSkillsRepository.findById(id).orElseThrow(() -> new Exception("Skill not found"));
    jobSkillsRepository.delete(skill);
  }

  @Override
  public Set<JobSkill> getSkillsByIds(Set<Long> ids) throws Exception {
    Set<JobSkill> skills = new HashSet<>(jobSkillsRepository.findAllById(ids));
    return skills;
  }

  private String generateUniqueSlug(String name) {
    String base =
        name.toLowerCase().replaceAll("[^a-z0-9\\s-]", "").trim().replaceAll("[\\s-]", "-");
    if (!jobSkillsRepository.existsBySlug(base)) {
      return base;
    }
    int counter = 1;
    while (jobSkillsRepository.existsBySlug(base + "-" + counter)) {
      counter++;
    }
    return base + "-" + counter;
  }
}
