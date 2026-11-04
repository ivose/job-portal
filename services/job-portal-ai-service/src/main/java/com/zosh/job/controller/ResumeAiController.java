package com.zosh.job.controller;

import com.zosh.job.payload.AiTextResponse;
import com.zosh.job.payload.CareerFeedbackRequest;
import com.zosh.job.payload.CareerFeedbackResponse;
import com.zosh.job.payload.ResumeImprovementRequest;
import com.zosh.job.payload.ResumeImprovementResponse;
import com.zosh.job.payload.ResumeSummaryRequest;
import com.zosh.job.payload.WorkExperienceBulletRequest;
import com.zosh.job.payload.WorkExperienceBulletsResponse;
import com.zosh.job.service.ResumeAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai/resume")
public class ResumeAiController {

  private final ResumeAiService resumeAiService;

  @PostMapping("/summary")
  public ResponseEntity<AiTextResponse> generateSummary(
      @Valid @RequestBody ResumeSummaryRequest req) throws Exception {
    return ResponseEntity.ok(resumeAiService.generateProfessionalSummary(req));
  }

  @PostMapping("/experience-bullets")
  public ResponseEntity<WorkExperienceBulletsResponse> generateExperienceBullets(
      @Valid @RequestBody WorkExperienceBulletRequest req) throws Exception {
    return ResponseEntity.ok(resumeAiService.generateWorkExperienceBullets(req));
  }

  @PostMapping("/improvements")
  public ResponseEntity<ResumeImprovementResponse> getImprovementTips(
      @Valid @RequestBody ResumeImprovementRequest req) throws Exception {
    return ResponseEntity.ok(resumeAiService.getResumeImprovementTips(req));
  }

  @PostMapping("/career-feedback")
  public ResponseEntity<CareerFeedbackResponse> generateCareerFeedback(
      @Valid @RequestBody CareerFeedbackRequest req) throws Exception {
    return ResponseEntity.ok(resumeAiService.generateCareerFeedback(req));
  }
}
