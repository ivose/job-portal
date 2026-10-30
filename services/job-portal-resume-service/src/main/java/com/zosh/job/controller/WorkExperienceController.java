package com.zosh.job.controller;

import com.zosh.job.dto.WorkExperienceResponse;
import com.zosh.job.payload.AddWorkExperience;
import com.zosh.job.service.WorkExperienceService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes/{resumeId}/work-experiences")
@RequiredArgsConstructor
public class WorkExperienceController {

  private final WorkExperienceService workExperienceService;

  @PostMapping
  public ResponseEntity<WorkExperienceResponse> addWorkExperience(
      @PathVariable Long resumeId,
      @RequestHeader("X-User-Id") Long candidateId,
      @Valid @RequestBody AddWorkExperience request)
      throws Exception {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(workExperienceService.addWorkExperience(resumeId, candidateId, request));
  }

  @GetMapping
  public ResponseEntity<List<WorkExperienceResponse>> getWorkExperiences(
      @PathVariable Long resumeId) throws Exception {

    return ResponseEntity.ok(workExperienceService.getWorkExperiences(resumeId));
  }

  @PutMapping("/{workExperienceId}")
  public ResponseEntity<WorkExperienceResponse> updateWorkExperience(
      @PathVariable Long resumeId,
      @PathVariable Long workExperienceId,
      @RequestHeader("X-User-Id") Long candidateId,
      @Valid @RequestBody AddWorkExperience request)
      throws Exception {

    return ResponseEntity.ok(
        workExperienceService.updateWorkExperience(
            resumeId, workExperienceId, candidateId, request));
  }

  @DeleteMapping("/{workExperienceId}")
  public ResponseEntity<Void> deleteWorkExperience(
      @PathVariable Long resumeId,
      @PathVariable Long workExperienceId,
      @RequestHeader("X-User-Id") Long candidateId)
      throws Exception {

    workExperienceService.deleteWorkExperience(resumeId, workExperienceId, candidateId);

    return ResponseEntity.noContent().build();
  }
}
