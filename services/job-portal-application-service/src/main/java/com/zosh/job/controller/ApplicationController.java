package com.zosh.job.controller;

import com.zosh.job.dto.ApiResponse;
import com.zosh.job.dto.ApplicationResponse;
import com.zosh.job.payload.CompanyApplicationFilterRequest;
import com.zosh.job.payload.CreateApplicationRequest;
import com.zosh.job.payload.UpdateApplicationStatusRequest;
import com.zosh.job.payload.WithdrawApplicationRequest;
import com.zosh.job.service.ApplicationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

  private final ApplicationService applicationService;

  @PostMapping
  public ResponseEntity<ApplicationResponse> createApplication(
      @RequestHeader("X-User-Id") Long candidateId,
      @RequestBody @Valid CreateApplicationRequest request)
      throws Exception {
    return ResponseEntity.ok(applicationService.createApplication(candidateId, request));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApplicationResponse> getApplicationById(@PathVariable Long id)
      throws Exception {
    return ResponseEntity.ok(applicationService.getApplicationById(id));
  }

  @GetMapping("/my")
  public ResponseEntity<List<ApplicationResponse>> getMyApplications(
      @RequestHeader("X-User-Id") Long candidateId) throws Exception {
    return ResponseEntity.ok(applicationService.getMyApplications(candidateId));
  }

  @GetMapping("/job/{jobId}")
  public ResponseEntity<List<ApplicationResponse>> getApplicationsByJobId(
      @PathVariable Long jobId, @ModelAttribute CompanyApplicationFilterRequest filter)
      throws Exception {
    return ResponseEntity.ok(applicationService.getApplicationsForJob(jobId, filter));
  }

  @GetMapping("/company")
  public ResponseEntity<List<ApplicationResponse>> getApplicationsByCompanyId(
      @RequestHeader("X-User-Id") Long userId,
      @ModelAttribute CompanyApplicationFilterRequest filter)
      throws Exception {
    return ResponseEntity.ok(applicationService.getApplicatonsForCompany(userId, filter));
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<ApplicationResponse> updateStatus(
      @PathVariable Long id,
      @RequestHeader("X-User-Id") Long employerId,
      @RequestBody @Valid UpdateApplicationStatusRequest req)
      throws Exception {
    return ResponseEntity.ok(applicationService.updateStatus(id, employerId, req.getStatus()));
  }

  @PatchMapping("/{id}/withdraw")
  public ResponseEntity<ApplicationResponse> withdraw(
      @PathVariable Long id,
      @RequestHeader("X-User-Id") Long candidateId,
      @RequestBody @Valid WithdrawApplicationRequest req)
      throws Exception {
    return ResponseEntity.ok(applicationService.withdraw(id, candidateId, req));
  }

  @PatchMapping("/{id}/star")
  public ResponseEntity<ApplicationResponse> toggleStar(
      @PathVariable Long id, @RequestHeader("X-User-Id") Long employerId) throws Exception {
    return ResponseEntity.ok(applicationService.toggleStar(id, employerId));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse> delete(
      @PathVariable Long id, @RequestHeader("X-User-Id") Long candidateId) throws Exception {
    applicationService.deleteApplication(id, candidateId);
    return ResponseEntity.ok(new ApiResponse("Application deleted successfully", true));
  }
}
