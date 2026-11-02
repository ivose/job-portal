package com.zosh.job.controller;

import com.zosh.job.dto.ApiResponse;
import com.zosh.job.dto.SavedJobResponse;
import com.zosh.job.payload.SavedJobRequest;
import com.zosh.job.service.SavedJobService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/preferences/saved")
public class SavedJobController {
  private final SavedJobService savedJobService;

  @PostMapping
  public ResponseEntity<SavedJobResponse> saveJob(
      @RequestHeader("X-User-Id") Long candidateId, @Valid @RequestBody SavedJobRequest req)
      throws Exception {
    return ResponseEntity.ok(savedJobService.saveJob(candidateId, req));
  }

  @GetMapping
  public ResponseEntity<List<SavedJobResponse>> getMySavedJobs(
      @RequestHeader("X-User-Id") Long candidateId) throws Exception {
    return ResponseEntity.ok(savedJobService.getSavedJob(candidateId));
  }

  @GetMapping("/check")
  public ResponseEntity<Boolean> isSaved(
      @RequestHeader("X-User-Id") Long candidateId, @RequestParam Long jobId) throws Exception {
    return ResponseEntity.ok(savedJobService.isSaved(candidateId, jobId));
  }

  @DeleteMapping("/{savedJobId}")
  public ResponseEntity<ApiResponse> unsaveJob(
      @PathVariable Long savedJobId, @RequestHeader("X-User-Id") Long candidateId)
      throws Exception {
    savedJobService.unsaveJob(candidateId, savedJobId);
    return ResponseEntity.ok(new ApiResponse("Job removed from saved list", true));
  }
}
