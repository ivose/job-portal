package com.zosh.job.controller;

import com.zosh.job.dto.LanguageResponse;
import com.zosh.job.payload.AddLanguageRequest;
import com.zosh.job.service.LanguageService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resumes/{resumeId}/languages")
@RequiredArgsConstructor
public class LanguageController {

  private final LanguageService languageService;

  @PostMapping
  public ResponseEntity<LanguageResponse> addLanguage(
      @PathVariable Long resumeId,
      @RequestHeader("X-User-Id") Long candidateId,
      @Valid @RequestBody AddLanguageRequest request)
      throws Exception {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(languageService.addLanguage(resumeId, candidateId, request));
  }

  @GetMapping
  public ResponseEntity<List<LanguageResponse>> getLanguages(@PathVariable Long resumeId)
      throws Exception {

    return ResponseEntity.ok(languageService.getLanguages(resumeId));
  }

  @PutMapping("/{languageId}")
  public ResponseEntity<LanguageResponse> updateLanguage(
      @PathVariable Long resumeId,
      @PathVariable Long languageId,
      @RequestHeader("X-User-Id") Long candidateId,
      @Valid @RequestBody AddLanguageRequest request)
      throws Exception {

    return ResponseEntity.ok(
        languageService.updateLanguage(languageId, resumeId, candidateId, request));
  }

  @DeleteMapping("/{languageId}")
  public ResponseEntity<Void> deleteLanguage(
      @PathVariable Long resumeId,
      @PathVariable Long languageId,
      @RequestHeader("X-User-Id") Long candidateId)
      throws Exception {

    languageService.deleteLanguage(languageId, resumeId, candidateId);

    return ResponseEntity.noContent().build();
  }
}
