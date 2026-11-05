package com.zosh.job.controller;

import com.zosh.job.payload.JobAlertSugestResponse;
import com.zosh.job.payload.JobAlertSuggestRequest;
import com.zosh.job.payload.SearchEncanceRequest;
import com.zosh.job.payload.SearchEncanceResponse;
import com.zosh.job.service.SearchAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai/search")
public class AiSearchController {

  private final SearchAiService searchAiService;

  @PostMapping("/enhance")
  public ResponseEntity<SearchEncanceResponse> enhanceSearch(
      @Valid @RequestBody SearchEncanceRequest req) throws Exception {
    return ResponseEntity.ok(searchAiService.enhanceSearch(req));
  }

  @PostMapping("/alert-suggestion")
  public ResponseEntity<JobAlertSugestResponse> suggestJobAlertCriteria(
      @Valid @RequestBody JobAlertSuggestRequest req) throws Exception {
    return ResponseEntity.ok(searchAiService.suggestJobAlertCriteria(req));
  }
}
