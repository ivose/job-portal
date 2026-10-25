package com.zosh.job.controller;

import com.zosh.job.dto.JobRequest;
import com.zosh.job.dto.JobResponse;
import com.zosh.job.payload.JobSearchRequest;
import com.zosh.job.service.JobService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

  private final JobService jobService;

  @PostMapping
  public ResponseEntity<JobResponse> createJob(
      @RequestHeader("X-User-Id") Long employerId, @Valid @RequestBody JobRequest request)
      throws Exception {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(jobService.createJob(employerId, request));
  }

  @GetMapping("/{id}")
  public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) throws Exception {
    return ResponseEntity.ok(jobService.getJobById(id));
  }

  @GetMapping
  public ResponseEntity<List<JobResponse>> getJobs(@ModelAttribute JobSearchRequest request)
      throws Exception {

    return ResponseEntity.ok(jobService.getJobs(request));
  }

  @GetMapping("/company/{companyId}")
  public ResponseEntity<List<JobResponse>> getJobsByCompanyId(@PathVariable Long companyId)
      throws Exception {

    return ResponseEntity.ok(jobService.getJobsByCompanyId(companyId));
  }

  @PutMapping("/{id}")
  public ResponseEntity<JobResponse> updateJob(
      @PathVariable Long id,
      @RequestHeader("X-User-Id") Long employerId,
      @Valid @RequestBody JobRequest request)
      throws Exception {

    return ResponseEntity.ok(jobService.updateJob(id, employerId, request));
  }

  @PatchMapping("/{id}/publish")
  public ResponseEntity<JobResponse> publishJob(
      @PathVariable Long id, @RequestHeader("X-User-Id") Long employerId) throws Exception {

    return ResponseEntity.ok(jobService.publishJob(id, employerId));
  }

  @PatchMapping("/{id}/close")
  public ResponseEntity<JobResponse> closeJob(
      @PathVariable Long id, @RequestHeader("X-User-Id") Long employerId) throws Exception {

    return ResponseEntity.ok(jobService.closeJob(id, employerId));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<JobResponse> deleteJob(
      @PathVariable Long id, @RequestHeader("X-User-Id") Long employerId) throws Exception {

    return ResponseEntity.ok(jobService.deleteJob(id, employerId));
  }

  @PostMapping("/{id}/applications/count")
  public ResponseEntity<Void> incrementApplicationCount(@PathVariable Long id) throws Exception {

    jobService.incrementApplicationCount(id);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/admin")
  public ResponseEntity<List<JobResponse>> getAllJobsAdmin() throws Exception {

    return ResponseEntity.ok(jobService.getAllJobsAdmin());
  }
}
