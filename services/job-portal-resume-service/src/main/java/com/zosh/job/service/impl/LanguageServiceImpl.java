package com.zosh.job.service.impl;

import com.zosh.job.dto.LanguageResponse;
import com.zosh.job.mapper.ResumeMapper;
import com.zosh.job.model.Language;
import com.zosh.job.model.Resume;
import com.zosh.job.payload.AddLanguageRequest;
import com.zosh.job.respository.LanguageRepository;
import com.zosh.job.service.LanguageService;
import com.zosh.job.service.ResumeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LanguageServiceImpl implements LanguageService {

  private final ResumeService resumeService;
  private final LanguageRepository languageRepository;

  @Override
  public LanguageResponse addLanguage(Long resumeId, Long candidateId, AddLanguageRequest req)
      throws Exception {
    Resume resume = resumeService.getResumeEntity(resumeId);
    assertOwner(resume, candidateId);
    Language lang =
        Language.builder()
            .resume(resume)
            .languageName(req.getLanguageName())
            .proficiency(req.getProficiency())
            .displayOrder(req.getDisplayOrder() == null ? 0 : req.getDisplayOrder())
            .build();
    Language saved = languageRepository.save(lang);
    return ResumeMapper.toLanguageResponse(saved);
  }

  @Override
  public List<LanguageResponse> getLanguages(Long resumeId) throws Exception {
    return languageRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId).stream()
        .map(ResumeMapper::toLanguageResponse)
        .toList();
  }

  @Override
  public LanguageResponse updateLanguage(
      Long languageId, Long resumeId, Long candidateId, AddLanguageRequest req) throws Exception {
    Language language =
        languageRepository
            .findById(languageId)
            .orElseThrow(() -> new Exception("Language not found"));
    assertOwner(language.getResume(), candidateId);
    assertResume(language, resumeId);
    language.setLanguageName(req.getLanguageName());
    language.setProficiency(req.getProficiency());
    if (req.getDisplayOrder() != null) {
      language.setDisplayOrder(req.getDisplayOrder());
    }
    Language saved = languageRepository.save(language);
    return ResumeMapper.toLanguageResponse(saved);
  }

  @Override
  public void deleteLanguage(Long languageId, Long resumeId, Long candidateId) throws Exception {
    Language language =
        languageRepository
            .findById(languageId)
            .orElseThrow(() -> new Exception("Language not found"));
    assertOwner(language.getResume(), candidateId);
    assertResume(language, resumeId);
    languageRepository.delete(language);
  }

  private void assertOwner(Resume resume, Long candidateId) throws Exception {
    if (!resume.getCandidateId().equals(candidateId)) {
      throw new Exception("You are not the owner of this resume");
    }
  }

  private void assertResume(Language language, Long resumeId) throws Exception {
    if (!language.getResume().getId().equals(resumeId)) {
      throw new Exception("Language does not belong to this resume");
    }
  }
}
