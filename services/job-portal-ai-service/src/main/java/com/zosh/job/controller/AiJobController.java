package com.zosh.job.controller;

import com.zosh.job.payload.AiTextResponse;
import com.zosh.job.payload.JobDescriptionRequest;
import com.zosh.job.payload.SalaryRangeRequest;
import com.zosh.job.payload.SalaryRangeResponse;
import com.zosh.job.service.JobAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai/job")
public class AiJobController {

  private final JobAiService jobAiService;

  @PostMapping("/describe")
  public ResponseEntity<AiTextResponse> generateJobDescription(
      @Valid @RequestBody JobDescriptionRequest request) throws Exception {
    return ResponseEntity.ok(jobAiService.generateJobDescription(request));
  }

  @PostMapping("/requirements")
  public ResponseEntity<AiTextResponse> generateJobRequirements(
      @RequestParam String title, @RequestParam(required = false) String category)
      throws Exception {
    return ResponseEntity.ok(jobAiService.generateJobRequirements(title, category));
  }

  @PostMapping("/salary-suggestion")
  public ResponseEntity<SalaryRangeResponse> generateSalaryRange(
      @Valid @RequestBody SalaryRangeRequest request) throws Exception {
    return ResponseEntity.ok(jobAiService.generateSalaryRange(request));
  }

  @PostMapping("/skills-recommendation")
  public ResponseEntity<AiTextResponse> recommendedSkillsForJob(
      @RequestParam String jobTitle, @RequestParam(required = false) String description)
      throws Exception {
    return ResponseEntity.ok(jobAiService.recommendedSkillsForJob(jobTitle, description));
  }

  @PostMapping("/responsibilities")
  public ResponseEntity<AiTextResponse> generateJobResponsibilities(
      @RequestParam String title, @RequestParam(required = false) String category)
      throws Exception {
    return ResponseEntity.ok(jobAiService.generateJobResponsibilities(title, category));
  }

  @PostMapping("/benefits")
  public ResponseEntity<AiTextResponse> generateJobBenefits(
      @RequestParam String title,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String jobType)
      throws Exception {
    return ResponseEntity.ok(jobAiService.generateJobBenefits(title, category, jobType));
  }

  @PostMapping("/tags-recommendations")
  public ResponseEntity<AiTextResponse> recommendTagForJob(
      @RequestParam String jobTitle, @RequestParam String description) throws Exception {
    return ResponseEntity.ok(jobAiService.recommendTagForJob(jobTitle, description));
  }
}
