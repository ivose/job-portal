package com.zosh.job.service;

import com.zosh.job.payload.AiTextResponse;
import com.zosh.job.payload.CoverLetterRequest;
import com.zosh.job.payload.ScreeningScoreRequest;
import com.zosh.job.payload.ScreeningScoreResponse;
import com.zosh.job.payload.SkillGapRequest;
import com.zosh.job.payload.SkillsGapResponse;

public interface ApplicationAiService {
  AiTextResponse generateCoverLetter(CoverLetterRequest req) throws Exception;

  ScreeningScoreResponse scoreCandidate(ScreeningScoreRequest req) throws Exception;

  SkillsGapResponse anallyseSkillGap(SkillGapRequest req) throws Exception;
}
