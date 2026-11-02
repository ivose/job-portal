package com.zosh.job.service.impl;

import com.zosh.job.dto.ApplicationNoteResponse;
import com.zosh.job.mapper.ApplicationMapper;
import com.zosh.job.model.Application;
import com.zosh.job.model.ApplicationNote;
import com.zosh.job.payload.AddApplicationNoteRequest;
import com.zosh.job.repository.ApplicationNoteRepository;
import com.zosh.job.service.ApplicationNoteService;
import com.zosh.job.service.ApplicationService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationNoteServiceImpl implements ApplicationNoteService {

  private final ApplicationService applicationService;
  private final ApplicationNoteRepository applicationNoteRepository;

  @Override
  public ApplicationNoteResponse addNote(
      Long applicationId, Long employerId, AddApplicationNoteRequest req) throws Exception {
    Application application = applicationService.getApplicationEntity(applicationId);
    assertEmployer(application, employerId);
    ApplicationNote applicationNote =
        ApplicationNote.builder()
            .application(application)
            .addedByUserId(employerId)
            .content(req.getContent())
            .build();
    ApplicationNote saveNote = applicationNoteRepository.save(applicationNote);
    return ApplicationMapper.toNoteResponse(saveNote);
  }

  @Override
  public List<ApplicationNoteResponse> getNotesByApplication(Long applicationId, Long employerId)
      throws Exception {
    Application application = applicationService.getApplicationEntity(applicationId);
    assertEmployer(application, employerId);
    return applicationNoteRepository.findByApplicationId(applicationId).stream()
        .map(ApplicationMapper::toNoteResponse)
        .toList();
  }

  @Override
  public void delete(Long applicationId, Long noteId, Long employerId) throws Exception {
    Application application = applicationService.getApplicationEntity(applicationId);
    assertEmployer(application, employerId);
    ApplicationNote note =
        applicationNoteRepository
            .findById(noteId)
            .orElseThrow(() -> new Exception("Note not found"));
    if (!note.getApplication().getId().equals(applicationId)) {
      throw new Exception("Note does not belong to this application");
    }
    applicationNoteRepository.delete(note);
  }

  private void assertEmployer(Application application, Long employerId) throws Exception {
    if (!application.getEmployerId().equals(employerId)) {
      throw new Exception("You are not the employer for this application");
    }
  }
}
