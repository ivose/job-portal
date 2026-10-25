package com.zosh.job.controller;

import com.zosh.job.dto.JobSkillResponse;
import com.zosh.job.mapper.JobSkillMapper;
import com.zosh.job.model.JobSkill;
import com.zosh.job.payload.JobSkillRequest;
import com.zosh.job.service.JobSkillService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/job-skills")
@RequiredArgsConstructor
public class JobSkillController {

  private final JobSkillService jobSkillService;

  @PostMapping
  public ResponseEntity<JobSkillResponse> createJobSkill(
      @Valid @RequestBody JobSkillRequest request) throws Exception {

    return ResponseEntity.status(HttpStatus.CREATED).body(jobSkillService.createJobSkill(request));
  }

  @GetMapping
  public ResponseEntity<List<JobSkillResponse>> getAllSkills() throws Exception {

    return ResponseEntity.ok(jobSkillService.getAllJobSkills());
  }

  @GetMapping("/{id}")
  public ResponseEntity<JobSkillResponse> getSkillById(@PathVariable Long id) throws Exception {

    return ResponseEntity.ok(jobSkillService.getSkillById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<JobSkillResponse> updateSkill(
      @PathVariable Long id, @Valid @RequestBody JobSkillRequest request) throws Exception {

    return ResponseEntity.ok(jobSkillService.updateSkill(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSkill(@PathVariable Long id) throws Exception {

    jobSkillService.deleteSkill(id);

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/by-ids")
  public ResponseEntity<Set<JobSkillResponse>> getSkillsByIds(@RequestBody Set<Long> ids)
      throws Exception {

    Set<JobSkill> skills = jobSkillService.getSkillsByIds(ids);

    Set<JobSkillResponse> responses =
        skills.stream().map(JobSkillMapper::toJobSkillResponse).collect(Collectors.toSet());

    return ResponseEntity.ok(responses);
  }
}
