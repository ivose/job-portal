package com.zosh.job.controller;

import com.zosh.job.domain.CompanyStatus;
import com.zosh.job.domain.CompanyType;
import com.zosh.job.domain.IndustryType;
import com.zosh.job.dto.ApiResponse;
import com.zosh.job.dto.CompanyRequest;
import com.zosh.job.dto.CompanyResponse;
import com.zosh.job.service.CompanyService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {

  private final CompanyService companyService;

  @PostMapping
  public ResponseEntity<CompanyResponse> createCompany(
      @RequestHeader("X-User-Id") Long ownerId, @Valid @RequestBody CompanyRequest companyRequest)
      throws Exception {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(companyService.createCompany(ownerId, companyRequest));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Long id) throws Exception {

    return ResponseEntity.ok(companyService.getCompanyById(id));
  }

  @GetMapping("/my")
  public ResponseEntity<CompanyResponse> getMyCompany(@RequestHeader("X-User-Id") Long ownerId)
      throws Exception {

    return ResponseEntity.ok(companyService.getMyCompany(ownerId));
  }

  @GetMapping
  public ResponseEntity<List<CompanyResponse>> getAllCompanies(
      @RequestParam(required = false) CompanyType companyType,
      @RequestParam(required = false) IndustryType industryType,
      @RequestParam(required = false) CompanyStatus companyStatus)
      throws Exception {

    return ResponseEntity.ok(
        companyService.getAllCompanies(companyType, industryType, companyStatus));
  }

  @PutMapping("/{companyId}")
  public ResponseEntity<CompanyResponse> updateCompany(
      @PathVariable Long companyId,
      @RequestHeader("X-User-Id") Long ownerId,
      @Valid @RequestBody CompanyRequest companyRequest)
      throws Exception {

    return ResponseEntity.ok(companyService.updateCompany(companyId, ownerId, companyRequest));
  }

  @PatchMapping("/verify")
  public ResponseEntity<CompanyResponse> verifyCompany(@RequestHeader("X-User-Id") Long ownerId)
      throws Exception {

    return ResponseEntity.ok(companyService.verifyCompany(ownerId));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse> deleteCompany(
      @PathVariable Long id, @RequestHeader("X-User-Id") Long ownerId) throws Exception {

    companyService.deleteCompany(id, ownerId);

    return ResponseEntity.ok(new ApiResponse("Company deleted successfully", true));
  }

  @PatchMapping("/deactivate")
  public ResponseEntity<CompanyResponse> deactivateCompany(@RequestHeader("X-User-Id") Long ownerId)
      throws Exception {

    return ResponseEntity.ok(companyService.deactivateCompany(ownerId));
  }
}
