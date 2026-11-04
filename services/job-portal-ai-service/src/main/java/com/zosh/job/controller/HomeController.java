package com.zosh.job.controller;

import com.zosh.job.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  @GetMapping
  public ResponseEntity<ApiResponse> home() {
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setMessage("AI Service - ai-powered content generation for job portal");
    apiResponse.setStatus(true);
    return ResponseEntity.ok(apiResponse);
  }
}
