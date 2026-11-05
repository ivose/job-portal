package com.zosh.job.controller;

import com.zosh.job.payload.AiTextResponse;
import com.zosh.job.payload.CoverLetterRequest;
import com.zosh.job.payload.ScreeningScoreRequest;
import com.zosh.job.payload.ScreeningScoreResponse;
import com.zosh.job.payload.SkillGapRequest;
import com.zosh.job.payload.SkillsGapResponse;
import com.zosh.job.service.ApplicationAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai/application")
public class AiApplicationController {
  private final ApplicationAiService applicationAiService;

  @PostMapping("/cover-letter")
  public ResponseEntity<AiTextResponse> generateCoverLetter(
      @Valid @RequestBody CoverLetterRequest req) throws Exception {
    return ResponseEntity.ok(applicationAiService.generateCoverLetter(req));
  }

  @PostMapping("/screening-score")
  public ResponseEntity<ScreeningScoreResponse> scoreCandidate(
      @Valid @RequestBody ScreeningScoreRequest req) throws Exception {
    return ResponseEntity.ok(applicationAiService.scoreCandidate(req));
  }

  @PostMapping("/skill-gap")
  public ResponseEntity<SkillsGapResponse> analyseSkillGap(@Valid @RequestBody SkillGapRequest req)
      throws Exception {
    return ResponseEntity.ok(applicationAiService.anallyseSkillGap(req));
  }
}
