package com.zosh.job.controller;

import com.zosh.job.domain.UserRole;
import com.zosh.job.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  @GetMapping
  public ResponseEntity<ApiResponse> home() {
    return ResponseEntity.ok(new ApiResponse("Home Controller -- " + UserRole.ROLE_EMPLOYER, true));
  }
}
