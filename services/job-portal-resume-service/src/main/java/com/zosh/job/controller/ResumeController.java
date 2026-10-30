package com.zosh.job.controller;

import com.zosh.job.dto.PersonalInfoResponse;
import com.zosh.job.dto.ResumeResponse;
import com.zosh.job.payload.CreateResumeRequest;
import com.zosh.job.service.ResumeService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

  private final ResumeService resumeService;

  @PostMapping
  public ResponseEntity<ResumeResponse> createResume(
      @RequestHeader("X-User-Id") Long candidateId, @Valid @RequestBody CreateResumeRequest request)
      throws Exception {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(resumeService.createResume(candidateId, request));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResumeResponse> getResumeById(
      @PathVariable Long id, @RequestHeader("X-User-Id") Long candidateId) throws Exception {

    return ResponseEntity.ok(resumeService.getResumeById(id, candidateId));
  }

  @GetMapping("/my")
  public ResponseEntity<List<ResumeResponse>> getMyResumes(
      @RequestHeader("X-User-Id") Long candidateId) throws Exception {

    return ResponseEntity.ok(resumeService.getMyResumes(candidateId));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResumeResponse> updateResume(
      @PathVariable Long id,
      @RequestHeader("X-User-Id") Long candidateId,
      @Valid @RequestBody CreateResumeRequest request)
      throws Exception {

    return ResponseEntity.ok(resumeService.updateResume(id, candidateId, request));
  }

  @PatchMapping("/{id}/personal-info")
  public ResponseEntity<ResumeResponse> updatePersonalInfo(
      @PathVariable Long id,
      @RequestHeader("X-User-Id") Long candidateId,
      @RequestBody PersonalInfoResponse request)
      throws Exception {

    return ResponseEntity.ok(resumeService.updatePersonalInfo(id, candidateId, request));
  }

  @PatchMapping("/{id}/summary")
  public ResponseEntity<ResumeResponse> updateSummary(
      @PathVariable Long id,
      @RequestHeader("X-User-Id") Long candidateId,
      @RequestBody String summary)
      throws Exception {

    return ResponseEntity.ok(resumeService.updateSummary(id, candidateId, summary));
  }

  @PatchMapping("/{id}/default")
  public ResponseEntity<ResumeResponse> setDefaultResume(
      @PathVariable Long id, @RequestHeader("X-User-Id") Long candidateId) throws Exception {

    return ResponseEntity.ok(resumeService.setDefaultResume(id, candidateId));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteResume(
      @PathVariable Long id, @RequestHeader("X-User-Id") Long candidateId) throws Exception {

    resumeService.deleteResume(id, candidateId);

    return ResponseEntity.noContent().build();
  }
}
